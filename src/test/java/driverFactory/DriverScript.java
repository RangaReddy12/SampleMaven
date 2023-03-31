package driverFactory;

import org.openqa.selenium.WebDriver;

import commonFunctions.FunctionLibary;
import utilities.ExcelFileUtil;


public class DriverScript {
public static WebDriver driver;
String inputpath ="D:\\FreeOjt\\Maven_HybridFramework\\FileInput\\DataEngine.xlsx";
String outputpath ="D:\\FreeOjt\\Maven_HybridFramework\\FileOutput\\HybridResults.xlsx";
public void startTest()throws Throwable
{
	String Modulestatus="";
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	for(int i=1;i<=xl.rowCount("MasterTestCases");i++)
	{
		if(xl.getCellData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
		{
			String TCModule =xl.getCellData("MasterTestCases", i, 1);
			for(int j=1;j<=xl.rowCount(TCModule);j++)
			{
				String Description= xl.getCellData(TCModule, j, 0);
				String Object_Type =xl.getCellData(TCModule, j, 1);
				String Locator_Type = xl.getCellData(TCModule, j, 2);
				String Locator_Value =xl.getCellData(TCModule, j, 3);
				String Test_Data = xl.getCellData(TCModule, j, 4);
				try {
					if(Object_Type.equalsIgnoreCase("startBrowser"))
					{
						driver = FunctionLibary.startBrowser();
					}
					else if(Object_Type.equalsIgnoreCase("openUrl"))
					{
						FunctionLibary.openUrl(driver);
					}
					else if(Object_Type.equalsIgnoreCase("waitForElement"))
					{
						FunctionLibary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
					}
					else if(Object_Type.equalsIgnoreCase("typeAction"))
					{
						FunctionLibary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
					}
					else if(Object_Type.equalsIgnoreCase("clickAction"))
					{
						FunctionLibary.clickAction(driver, Locator_Type, Locator_Value);
					}
					else if(Object_Type.equalsIgnoreCase("validateTitle"))
					{
						FunctionLibary.validateTitle(driver, Test_Data);
					}
					else if(Object_Type.equalsIgnoreCase("closeBrowser"))
					{
						FunctionLibary.closeBrowser(driver);
					}
					else if(Object_Type.equalsIgnoreCase("mouseClick"))
					{
						FunctionLibary.mouseClick(driver);
					}
					else if(Object_Type.equalsIgnoreCase("stockTable"))
					{
						FunctionLibary.stockTable(driver, Test_Data);
					}
					else if(Object_Type.equalsIgnoreCase("captureData"))
					{
						FunctionLibary.captureData(driver, Locator_Type, Locator_Value);
					}
					else if(Object_Type.equalsIgnoreCase("supplierTable"))
					{
						FunctionLibary.supplierTable(driver);
					}
					xl.setCellData(TCModule, j, 5, "Pass", outputpath);
					Modulestatus="True";
				}catch(Throwable t)
				{
					System.out.println(t.getMessage());
					xl.setCellData(TCModule, j, 5, "Fail", outputpath);
					Modulestatus="False";
				}
				if(Modulestatus.equalsIgnoreCase("True"))
				{
					xl.setCellData("MasterTestCases", i, 3, "Pass", outputpath);
				}
				else
				{
					xl.setCellData("MasterTestCases", i, 3, "Fail", outputpath);
				}
			}
		}
		else
		{
			xl.setCellData("MasterTestCases", i, 3, "Blocked", outputpath);
		}
	}
}
}
