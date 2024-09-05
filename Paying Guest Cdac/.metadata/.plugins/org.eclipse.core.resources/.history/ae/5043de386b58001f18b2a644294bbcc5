package com.example.payingguest.service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.payingguest.entities.Bill;
import com.example.payingguest.entities.Bill.BillStatus;
import com.example.payingguest.entities.Booking;
import com.example.payingguest.entities.Booking.BookingStatus;
import com.example.payingguest.entities.PG;
import com.example.payingguest.entities.User;
import com.example.payingguest.exception.CustomException;
import com.example.payingguest.repository.BillRepo;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.activation.DataSource;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

@Service
public class BillService {

	@Autowired
	private BillRepo billRepoRef;

	@Autowired
	private UserService userServiceRef;

	@Autowired
	private BookingService bookingServiceRef;

	@Autowired
	private JavaMailSender mailSender;

	// @Autowired
	// private PgService pgServiceRef;
 
	public Bill getBillById(Long billId) {
		return billRepoRef.findById(billId)
				.orElseThrow(() -> new CustomException("Bill not found of  BillId:" + billId));
	}

	public List<Bill> getBillByUserId(Long userId) {
		List<Bill> bills = billRepoRef.findByUserRefUserId(userId);
		if (bills.isEmpty()) {
			throw new CustomException("No bill available of UserId:" + userId);
		} else {
			return bills;
		}
	}
	


	public String deleteBillbyBillId(Long billId) {
		Bill billRef = getBillById(billId);
		if (billRef != null) {
			billRepoRef.deleteById(billId);
			return "bill deleted successfully";
		} else {
			throw new CustomException("No bill available bill id " + billId);
		}
	}

	@Scheduled(cron = "0 23 10 11 * ?")
	public void generateMonthlyBillsForAllUsers() throws Exception {
		List<User> users = userServiceRef.GetAllUsersByRole("user");
		for (User user : users) {
			generateBillForUser(user);
		}
	}

	public String generateBillForUser(User user) throws Exception {

		List<Booking> bookings = bookingServiceRef.getBookingByUserId(user.getUserId());

		Bill billRef = null;
		for (Booking booking : bookings) {
			System.out.println(booking);
			if ((booking.getBookingStatus() != BookingStatus.CANCELLED
					&& booking.getBookingStatus() != BookingStatus.CHECKEDOUT) || booking.getCheckOutDate() != null) {
				LocalDate lastBillingDate = getLastBillingDate(user);
				LocalDate startDate = (lastBillingDate == null) ? booking.getBookingDate()
						: lastBillingDate.plusDays(1);
				LocalDate endDate = startDate.plusMonths(1);

				if (endDate.isAfter(LocalDate.now())) {
					endDate = LocalDate.now();
				}

				if (endDate.isAfter(startDate)) {
					double amount =Math.ceil(calculateAmount(booking, startDate, endDate));
					if (amount > 0) {
						Bill bill = new Bill();
						bill.setAmount(amount);
						bill.setStatus(BillStatus.UNPAID); // Assuming unpaid
						bill.setBillingPeriodStart(startDate);
						bill.setBillingPeriodEnd(endDate);
						bill.setCreatedDate(LocalDate.now());
						bill.setUserRef(user);
						billRef = billRepoRef.save(bill);

						sendBillNotification(user, bill);

					}
				}
			}
		}
		return billRef != null ? "billcreated" : "not bills pending";

	}

	private LocalDate getLastBillingDate(User user) {
		List<Bill> bills = billRepoRef.findByUserRefUserId(user.getUserId());
		return bills.stream().filter(bill -> bill.getStatus() == BillStatus.PAID) // Adjust the condition as needed
				.map(Bill::getBillingPeriodEnd).max(LocalDate::compareTo).orElse(null);
	}

	private double calculateAmount(Booking booking, LocalDate startDate, LocalDate endDate) {
		long daysStayed = ChronoUnit.DAYS.between(startDate, endDate) ;

		return (booking.getRoomRef().getRent())/30  * daysStayed;
	}

	public void sendBillNotification(User user, Bill bill) throws Exception {
		String to = user.getEmail();
		String subject = "Your Monthly Bill";
		String text = "Dear " + user.getName() + ",\n\nYour bill for the period "
				+ bill.getBillingPeriodStart().toString() + ", to" + bill.getBillingPeriodEnd().toString() + "is  "
				+String.format(".2%f", bill.getAmount()) +".\n"
				+ "Please pay the amount .\n\nThank you for staying with us!\n\nBest regards,\nThe Management Team";

		MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        DataSource dataSource = new ByteArrayDataSource(generatePdf(bill), "application/pdf");
        helper.addAttachment(" ",dataSource);
		mailSender.send(message);
	}

	
	public byte[] generatePdf(Bill billRef) throws Exception {
		User userRef = billRef.getUserRef();
		Long userId = userRef.getUserId();
		List<Booking> bookings = bookingServiceRef.getBookingByUserId(userId);
		PG pgRef = null;

		if (!bookings.isEmpty()) {
			pgRef = bookings.get(0).getRoomRef().getPgRef(); // Adjust according to your actual data model
		}

		// Create a new PDF document
		Document document = new Document(PageSize.A4);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		// Create a PdfWriter instance
		@SuppressWarnings("unused")
		PdfWriter writer = PdfWriter.getInstance(document, outputStream);

		// Open the document
		document.open();

		// Set font and styles
		Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
		Font normalFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
		// Font smallBoldFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);

		// Define base colors
		BaseColor goldColor = new BaseColor(255, 215, 0);
		BaseColor lightYellowColor = new BaseColor(255, 255, 224);
		// BaseColor lightGreenColor = new BaseColor(173, 255, 47);

		PdfPTable headerTable = new PdfPTable(1);
		headerTable.setWidthPercentage(40);
		headerTable.setSpacingAfter(20);
		headerTable.setHorizontalAlignment(Element.ALIGN_CENTER);
		// headerTable.setWidths(new int[] { });

		PdfPCell headerTextCell = new PdfPCell();
		headerTextCell.setBorder(Rectangle.NO_BORDER);
		headerTextCell.setBackgroundColor(goldColor);
		headerTextCell.setPadding(10);
		headerTextCell.addElement(new Phrase(pgRef.getPgName(), headerFont));
		headerTextCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		headerTextCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		headerTable.addCell(headerTextCell);
		headerTable.addCell(new Paragraph());
		document.add(headerTable);

		document.add(new Paragraph("PG Details"));

		PdfPTable PgTable = new PdfPTable(2);
		PgTable.setWidthPercentage(80);
		PgTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		PgTable.setWidths(new int[] { 2, 4 });

		PgTable.addCell(createHeaderCell("PG Name:", headerFont, BaseColor.WHITE));
		PgTable.addCell(new Phrase(pgRef.getPgName(), normalFont));
		PgTable.addCell(createHeaderCell("Owner Name:", headerFont, BaseColor.WHITE));
		PgTable.addCell(new Phrase(pgRef.getOwnerName(), normalFont));
		PgTable.addCell(createHeaderCell("Contact No:", headerFont, BaseColor.WHITE));
		PgTable.addCell(new Phrase(pgRef.getContactNo(), normalFont));
		PgTable.addCell(createHeaderCell("PG Address:", headerFont, BaseColor.WHITE));
		PgTable.addCell(new Phrase(pgRef.getAddress(), normalFont));

		for (int i = 0; i < PgTable.getRows().size(); i++) {
			for (PdfPCell cell : PgTable.getRow(i).getCells()) {
				if (cell != null) {
					cell.setBorder(PdfPCell.NO_BORDER);
				}
			}
		}

		document.add(PgTable);

		// Empty space after header
		document.add(new Paragraph(
				"------------------------------------------------------------------------------------------------ "));
		document.add(new Paragraph("User Details"));

		// Add customer and invoice details section (blank rows)
		PdfPTable detailsTable = new PdfPTable(2);
		detailsTable.setWidthPercentage(80);
		detailsTable.setSpacingBefore(10);
		detailsTable.setSpacingAfter(20);
		detailsTable.setWidths(new int[] { 2, 4 });

		detailsTable.setHorizontalAlignment(Element.ALIGN_LEFT);

		detailsTable.addCell(createHeaderCell("Name:", headerFont, BaseColor.WHITE));
		detailsTable.addCell(new Phrase(userRef.getName(), normalFont));
		detailsTable.addCell(createHeaderCell("mobile No:", headerFont, BaseColor.WHITE));
		detailsTable.addCell(new Phrase(userRef.getMobileNo(), normalFont));
		detailsTable.addCell(createHeaderCell("Address:", headerFont, BaseColor.WHITE));
		detailsTable.addCell(new Phrase(userRef.getAddress(), normalFont));
		detailsTable.addCell(createHeaderCell("Email:", headerFont, BaseColor.WHITE));
		detailsTable.addCell(new Phrase(userRef.getEmail(), normalFont));

		for (int i = 0; i < detailsTable.getRows().size(); i++) {
			for (PdfPCell cell : detailsTable.getRow(i).getCells()) {
				if (cell != null) {
					cell.setBorder(PdfPCell.NO_BORDER);
				}
			}
		}

		document.add(detailsTable);

		// Add itemized table header row with a golden background
		PdfPTable itemTable = new PdfPTable(6);
		itemTable.setWidthPercentage(100);
		itemTable.setSpacingBefore(20);
		itemTable.setSpacingAfter(20);
		itemTable.setWidths(new int[] { 1, 2, 2, 2, 2, 2 });

		itemTable.addCell(createHeaderCell("Room No", headerFont, goldColor));
		itemTable.addCell(createHeaderCell("Checked in", headerFont, goldColor));
		itemTable.addCell(createHeaderCell("Checked out", headerFont, goldColor));
		itemTable.addCell(createHeaderCell("Amount", headerFont, goldColor));
		itemTable.addCell(createHeaderCell("Extra charged", headerFont, goldColor));
		itemTable.addCell(createHeaderCell("Total", headerFont, goldColor));

		double totalRent = 0;
		for (Booking booking : bookings) {
			if (!booking.getBookingStatus().equals(BookingStatus.CANCELLED)
					&& !booking.getBookingStatus().equals(BookingStatus.CHECKEDOUT)) {

				itemTable.addCell(new Phrase(booking.getRoomRef().getRoomNumber().toString()));
				itemTable.addCell(new Phrase(booking.getBookingDate().toString()));
				itemTable.addCell(new Phrase(billRef.getBillingPeriodEnd().toString()));
				itemTable.addCell(new Phrase(String.valueOf(booking.getRoomRef().getRent())));
				itemTable.addCell(new Phrase("-"));
				itemTable.addCell(new Phrase(String.valueOf(booking.getRoomRef().getRent())));
				totalRent += booking.getRoomRef().getRent();
			}
		}

		document.add(itemTable);

//	    //Add notes section (blank rows)
//	    PdfPTable notesTable = new PdfPTable(2);
//	    notesTable.setWidthPercentage(100);
//	    notesTable.setSpacingBefore(20);
//	    notesTable.setSpacingAfter(20);
//	    notesTable.addCell(createEmptyCell(1, 30));
//	    notesTable.addCell(createEmptyCell(1, 30));
//	    document.add(notesTable);

		// Add total section with different background colors
		PdfPTable totalTable = new PdfPTable(2);
		totalTable.setWidthPercentage(55);
		totalTable.setSpacingAfter(20);
		totalTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

		totalTable.setWidths(new int[] { 2, 1 });

		totalTable.addCell(createHeaderCell("Subtotal", headerFont, lightYellowColor));
		totalTable.addCell(createHeaderCell(String.format("%.2f", totalRent), normalFont, lightYellowColor));

		totalTable.addCell(createHeaderCell("Tax (0%)", headerFont, lightYellowColor));
		totalTable.addCell(createHeaderCell("", normalFont, lightYellowColor));

		totalTable.addCell(createHeaderCell("Total", headerFont, lightYellowColor));
		totalTable.addCell(createHeaderCell(String.format("%.2f", totalRent), normalFont, lightYellowColor));
		document.add(totalTable);

		PdfPTable statusTable = new PdfPTable(1);
		statusTable.setWidthPercentage(40);
		statusTable.setSpacingAfter(20);
		statusTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

		if (billRef.getStatus().equals(BillStatus.UNPAID))
			statusTable.addCell(createHeaderCell(billRef.getStatus().toString(), normalFont, BaseColor.RED));
		else
			statusTable.addCell(createHeaderCell(billRef.getStatus().toString(), normalFont, BaseColor.GREEN));

		document.add(statusTable);

		// Footer section
		Paragraph footer = new Paragraph("Thank you for staying with us!", normalFont);
		footer.setAlignment(Element.ALIGN_CENTER);
		footer.setSpacingBefore(30);
		document.add(footer);

		// Close the document
		document.close();

		// Get the byte array of the generated PDF
		return outputStream.toByteArray();
	}

	// Helper methods to create cells with specified styles and colors
	private PdfPCell createHeaderCell(String text, Font font, BaseColor backgroundColor) {
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setBackgroundColor(backgroundColor);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPadding(5);
		return cell;
	}

//	@SuppressWarnings("unused")
//	private PdfPCell createEmptyCell(int colspan, float height) {
//		PdfPCell cell = new PdfPCell(new Phrase(""));
//		cell.setColspan(colspan);
//		cell.setMinimumHeight(height);
//		cell.setBorder(Rectangle.NO_BORDER);
//		return cell;
//	}

}
