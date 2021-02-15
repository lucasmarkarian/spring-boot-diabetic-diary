package com.bolsadeideas.springboot.diabeticdiary.app.utilities;

import java.util.Calendar;

public class DaysOfWeek {
	
	public static final String[] DAYS = new String[] { "Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado" };
	
	public static final String formatDate(Calendar calendar) {
		String dateFormated = "  " + calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1);
		return dateFormated;
	}

}
