package com.infocms.junit;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <strong>BaseJunitCase</strong><br>
 * <br> 
 * <strong>Create on : 2012-2-10<br></strong>
 * <p>
 * <strong>Copyright (C) Ecointel Software Co.,Ltd.<br></strong>
 * <p>
 * @author peng.shi peng.shi@ecointel.com.cn<br>
 * @version <strong>ecointel-epp v1.0.0</strong><br>
 */
@ContextConfiguration(locations = { "classpath*:spring.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseJunitCase
{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
}
