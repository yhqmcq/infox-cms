package com.infox.common.mail;

public enum MimeTypeEnum {

	TEXT {
		@Override
		public String getType() {return "text/plain";}
	},HTML {
		@Override
		public String getType() {return "text/html";}
	} ;
	
	public abstract String getType() ;
}
