package org.qamation.jmeter.java.sampler.browser;

import org.qamation.commons.webdriver.WebDriverFactory;
import org.qamation.jmeter.java.sampler.abstracts.AbstractExtentionBrowser;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;
import org.openqa.selenium.WebDriver;

import java.net.URL;


public class CreateBrowser extends AbstractExtentionBrowser {
    protected static final String BROWSER_NAME = "SELECT BROWSER TO CREATE: Chrome, FF, Edge";
    protected static final String BROWSER_TYPE = "SELECT BROWSER TYPE: Local or Remote";
    protected static final String CHROME_DRIVER_PATH = "ENTER PATH TO CHROME DRIVER.";
    protected static final String EDGE_DRIVER_PATH = "ENTER PATH TO EDGE DRIVER.";
    protected static final String FF_DRIVER_PATH = "ENTER PATH TO FF DRIVER";

    protected String browserName;
    protected String browserType;
    protected String chromeDriverPath; // ../../Selenium/ChromeDriver/chromedriver.exe
    protected String ieDriverPath; // ../../Selenium/IEDriver/IEDriverServer.exe
    protected String ffDriverPath; // ../../Selenium/FFDriver/geckodriver.exe


    public Arguments getDefaultParameters() {
        Arguments defaultParameters = super.getDefaultParameters();
        defaultParameters.addArgument(BROWSER_NAME,"Chrome");
        defaultParameters.addArgument(BROWSER_TYPE,"Local");
        defaultParameters.addArgument(CHROME_DRIVER_PATH, "${CHROME_DRIVER_PATH}");
        defaultParameters.addArgument(EDGE_DRIVER_PATH,"${IE_DRIVER_PATH}");
        defaultParameters.addArgument(FF_DRIVER_PATH,"${FF_DRIVER_PATH}");
        return defaultParameters;
    }


    protected void readSamplerParameters() {
        super.readSamplerParameters();
        browserName = getSamplerParameterValue(BROWSER_NAME);
        browserType = getSamplerParameterValue(BROWSER_TYPE);
        chromeDriverPath = getSamplerParameterValue(CHROME_DRIVER_PATH);
        ieDriverPath = getSamplerParameterValue(EDGE_DRIVER_PATH);
        ffDriverPath = getSamplerParameterValue(FF_DRIVER_PATH);
    }

    @Override
    protected void toDo() {
        driver=selectDriver();
        setObjectIntoVariables(browserVariableName,driver);
    }



    @Override
    protected SampleResult assembleTestResult() {
        String message = browserType + " " + browserName + " browser is created.";
        result = setSuccess(null,message,message);
        return result;
    }

    @Override
    protected SampleResult assembleTestFailure(Exception e) {
        String message = browserType + " " + browserName + " browser is not created\n"+e.toString();
        result = setFailure(null,message,message);
        return result;
    }

    private WebDriver selectDriver() {
        WebDriver dr=null;
        if (browserType.equalsIgnoreCase("remote")) {
            URL hub = getHubUrl();
            if (browserName.equalsIgnoreCase("ff")) {
                dr = WebDriverFactory.createRemoteFFWebDriver(hub);
            }
            else if (browserName.equalsIgnoreCase("edge")) {
                dr = WebDriverFactory.createRemoteEdgeDriver(hub);
            }
            else dr = WebDriverFactory.createRemoteChromeDriver(hub);
        }
        else {
            if (browserName.equalsIgnoreCase("ff")) {
                dr = WebDriverFactory.createFFWebDriver(ffDriverPath);
            }
            if (browserName.equalsIgnoreCase("edge")) {
                dr = WebDriverFactory.createEdgeWebDriver(ieDriverPath);
            }
        }
        if (dr == null) dr = WebDriverFactory.createChromeWebDriver(chromeDriverPath);
        return dr;
    }
}
