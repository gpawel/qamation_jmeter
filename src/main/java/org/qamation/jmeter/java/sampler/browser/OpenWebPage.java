package org.qamation.jmeter.java.sampler.browser;

import org.qamation.jmeter.java.sampler.abstracts.AbstractExtentionPage;
import org.qamation.commons.utils.StringUtils;
import org.qamation.commons.web.page.Page;
import org.qamation.commons.web.page.WebPageFactory;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;

import java.net.MalformedURLException;
import java.net.URL;


public class OpenWebPage extends AbstractExtentionPage {

	protected static final String URL_TO_OPEN = "ENTER WEB PAGE TO BE OPENED";
	long start_ts;
	long end_ts;
	String url_string;
	
	public Arguments getDefaultParameters() {
        Arguments defaultParameters = super.getDefaultParameters();
        defaultParameters.addArgument(URL_TO_OPEN,"http://cbc.ca");
        return defaultParameters;
    }

	protected void readSamplerParameters() {
		super.readSamplerParameters();
		url_string = getSamplerParameterValue(URL_TO_OPEN);
	}

	@Override
	protected void toDo() {
		try {
			Page page = WebPageFactory.createPageInstance(pageImplementationClass, driver);
			start_ts = System.currentTimeMillis();
			end_ts = 0;
			page.openPage(new URL(url_string));
			end_ts = System.currentTimeMillis();
		}
		catch (MalformedURLException ex) {
			throw new RuntimeException("Unable to open a page by provided url string: "+ url_string);
		}
	}

	@Override
	protected SampleResult assembleTestResult() {
		String term = getDuration(end_ts - start_ts);
		String message = "PAGE at "+ url_string +" IS OPENED. "+term;
		SampleResult result = setSuccess(message,message,message);
		return result;
	}

	@Override
	protected SampleResult assembleTestFailure(Exception e) {
		String stackTrace = StringUtils.getStackTrace(e);
		SampleResult result = setFailure("Cannot open provided URL\n\n"+stackTrace,e);
		return result;
	}

}
