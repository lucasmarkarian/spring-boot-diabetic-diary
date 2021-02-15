package com.bolsadeideas.springboot.diabeticdiary.app.view.pdf;

import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;

class RoundedBorder implements PdfPCellEvent {
	public void cellLayout(PdfPCell cell, Rectangle rect, PdfContentByte[] canvas) {
		PdfContentByte cb = canvas[PdfPTable.BACKGROUNDCANVAS];
		cb.roundRectangle(rect.getLeft() + 1.5f, rect.getBottom() + 1.5f, rect.getWidth() - 3, rect.getHeight() - 3,
				4f);
		cb.stroke();
	}
}