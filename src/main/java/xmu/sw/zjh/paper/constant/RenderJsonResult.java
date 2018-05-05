package xmu.sw.zjh.paper.constant;

public class RenderJsonResult
{
	public static JsonResult renderSucessResult(String fileName)
	{
		JsonResult jsonResult = new JsonResult();
		jsonResult.setSuccess(true);
		jsonResult.setFileName(fileName);
		return jsonResult;
	}
	
	public static JsonResult renderErrorResult()
	{
		JsonResult jsonResult = new JsonResult();
		jsonResult.setSuccess(false);
		return jsonResult;
	}
}
