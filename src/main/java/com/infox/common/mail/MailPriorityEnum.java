package com.infox.common.mail;

public enum MailPriorityEnum {
	
	/** 紧急邮件 */URG {
		@Override
		public String getName() {return "1";}
	},/** 普通邮件*/NORMAL {
		@Override
		public String getName() {return "3";}
	},/** 缓慢邮件 */SLOW {
		@Override
		public String getName() {return "4";}
	};
	
	public abstract String getName() ;
	
}
