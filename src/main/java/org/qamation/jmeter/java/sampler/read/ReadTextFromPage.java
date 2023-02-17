package org.qamation.jmeter.java.sampler.read;


import org.qamation.commons.web.page.WebPageFactory;
import org.qamation.jmeter.java.sampler.abstracts.AbstractExtentionReadFromPage;
import org.qamation.commons.utils.StringUtils;
import org.qamation.commons.web.page.Page;
import org.apache.jmeter.config.Arguments;
import org.qamation.locator.LocatorFactory;


public class ReadTextFromPage  extends AbstractExtentionReadFromPage {
	
	protected static final String LENGTH_PARAMETER = "ENTER NUMBER OF SYMBOLS TO READ"; 
	
	private Page page;
	protected int length;
	
	@Override
	public Arguments getDefaultParameters() {
        Arguments defaultParameters = super.getDefaultParameters();
        defaultParameters.addArgument(LENGTH_PARAMETER,"");
        return defaultParameters;
    }
	
	@Override
	protected void readSamplerParameters() {
		super.readSamplerParameters();
		length = getLengthToRead();
	}
	
	private int getLengthToRead() {
		String readLength = getSamplerParameterValue(LENGTH_PARAMETER);
		if (readLength.isEmpty()) return 0;
		return StringUtils.convertStringToInt(readLength);
	}
	
	@Override
	protected Page createPage() {
		Page p = WebPageFactory.createPageInstance(pageImplementationClass, driver);
		return p;
		
	}

	@Override
	protected String readText() {
		String read_str = page.readTextFrom(LocatorFactory.getLocator(readFromlocationDescription));
		if (length > 0) return read_str.substring(0,length);
		return read_str;
	}
}
