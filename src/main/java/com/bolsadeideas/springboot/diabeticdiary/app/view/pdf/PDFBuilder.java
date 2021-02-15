package com.bolsadeideas.springboot.diabeticdiary.app.view.pdf;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.bolsadeideas.springboot.diabeticdiary.app.models.entity.ControlDiary;
import com.bolsadeideas.springboot.diabeticdiary.app.utilities.DaysOfWeek;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("test")
public class PDFBuilder extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String desde = (String) model.get("desde");
		String hasta = (String) model.get("hasta");
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM");
		
		@SuppressWarnings("unchecked")
		List<ControlDiary> controles = (List<ControlDiary>) model.get("controles");
		
		PdfPTable tablaTop = new PdfPTable(7);
		tablaTop.setWidthPercentage(110);
		tablaTop.setWidths(new float[] {.2f, .8f, .69f, .69f, .69f, .69f, 2.24f});
		
		PdfPCell cell = null;
		
		//Fila 1
		cell = new PdfPCell(new Phrase("Del " + desde + "\nal " + hasta));
		cell.setColspan(2);
		cell.setFixedHeight(30);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(184, 218, 255));
		tablaTop.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Antes del desayuno"));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setBackgroundColor(new Color(184, 218, 255));
		tablaTop.addCell(cell);
		
		
		cell = new PdfPCell(new Phrase("Antes del almuerzo"));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setBackgroundColor(new Color(184, 218, 255));
		tablaTop.addCell(cell);
		
		
		cell = new PdfPCell(new Phrase("Antes de la merienda"));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setBackgroundColor(new Color(184, 218, 255));
		tablaTop.addCell(cell);
		
		
		cell = new PdfPCell(new Phrase("Antes de la cena"));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setBackgroundColor(new Color(184, 218, 255));
		tablaTop.addCell(cell);
		
		
		cell = new PdfPCell(new Phrase("Observaciones"));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setBackgroundColor(new Color(184, 218, 255));
		tablaTop.addCell(cell);
		
		document.add(tablaTop);
		
		//Fila 2
		Calendar calendar1 = Calendar.getInstance();
		
		for(ControlDiary control: controles) {
			PdfPTable tabla = new PdfPTable(7);
			tabla.setWidthPercentage(110);
			tabla.setWidths(new float[] {.2f, .8f, .69f, .69f, .69f, .69f, 2.24f});
			
			calendar1.setTime(control.getDate());
			String strDate = dateFormat.format(calendar1.getTime());
			cell = new PdfPCell(new Phrase(DaysOfWeek.DAYS[calendar1.get(Calendar.DAY_OF_WEEK) - 1] + "  " + strDate));
			cell.setRowspan(3);
			cell.setRotation(90);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			tabla.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Glucemia"));
			cell.setPaddingLeft(4f);
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cell.setFixedHeight(35);
			tabla.addCell(cell);
			
			cell = new PdfPCell(new Phrase(control.getGlucemiaAntDes().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			tabla.addCell(cell);
			
			cell = new PdfPCell(new Phrase(control.getGlucemiaAntAlm().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			tabla.addCell(cell);
			
			cell = new PdfPCell(new Phrase(control.getGlucemiaAntMer().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			tabla.addCell(cell);
			
			cell = new PdfPCell(new Phrase(control.getGlucemiaAntCen().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			tabla.addCell(cell);
			
			cell = new PdfPCell(new Phrase(control.getGlucemiaObservaciones()));
			tabla.addCell(cell);
			
			//Fila 3
			cell = new PdfPCell(new Phrase("Insulina"));
			cell.setPaddingLeft(4f);
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cell.setFixedHeight(35);
			tabla.addCell(cell);
			
			cell = new PdfPCell(new Phrase(control.getInsulinaAntDes().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			tabla.addCell(cell);
			
			cell = new PdfPCell(new Phrase(control.getInsulinaAntAlm().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			tabla.addCell(cell);
			
			cell = new PdfPCell(new Phrase(control.getInsulinaAntMer().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			tabla.addCell(cell);
			
			cell = new PdfPCell(new Phrase(control.getInsulinaAntCen().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			tabla.addCell(cell);
			
			cell = new PdfPCell(new Phrase(control.getInsulinaObservaciones()));
			tabla.addCell(cell);
			
			//Fila 4
			cell = new PdfPCell(new Phrase("Correccion"));
			cell.setPaddingLeft(4f);
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cell.setFixedHeight(35);
			tabla.addCell(cell);
			
			cell = new PdfPCell(new Phrase(control.getCorreccionAntDes().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			tabla.addCell(cell);
			
			cell = new PdfPCell(new Phrase(control.getCorreccionAntAlm().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			tabla.addCell(cell);
			
			cell = new PdfPCell(new Phrase(control.getCorreccionAntMer().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			tabla.addCell(cell);
			
			cell = new PdfPCell(new Phrase(control.getCorreccionAntCen().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			tabla.addCell(cell);
			
			cell = new PdfPCell(new Phrase(control.getCorreccionObservaciones()));
			tabla.addCell(cell);
			
			tabla.setSpacingAfter(15);
			
			document.add(tabla);
		}
		
	}

}
