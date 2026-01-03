package com.abhibus.Components;

public interface FunctionBase {
    public void clickByXpath(String xpath);
    public void clickById(String id);
    public void clickByCssSelector(String cssSelector);
    public void verifyTextByXpath(String xpath,String data);
    public void enterDataByXpath(String xpath,String data);
    public void closeAllBrowsers();
}
