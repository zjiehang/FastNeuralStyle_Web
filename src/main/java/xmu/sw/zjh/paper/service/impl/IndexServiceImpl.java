package xmu.sw.zjh.paper.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import xmu.sw.zjh.paper.config.ConfigConstant;
import xmu.sw.zjh.paper.constant.GetTime;
import xmu.sw.zjh.paper.constant.JsonResult;
import xmu.sw.zjh.paper.constant.RenderJsonResult;
import xmu.sw.zjh.paper.service.IndexService;
import xmu.sw.zjh.paper.tensorflow.TensorflowModel;

@Service("indexService")
public class IndexServiceImpl implements IndexService
{
	
	public JsonResult getResultByController(
			MultipartFile contentFile,
			MultipartFile styleFile,
			String stylePath,
			HttpServletRequest request)
	{
		try
		{
			float[][][][] result = null;
			if(stylePath!=null)
			{
				result = getResultByContentAndStylePath(contentFile, stylePath, request);
			}
			else
			{
				result = getResultByContentAndStyle(contentFile,styleFile,request);
			}

			String fileName = GetTime.getCurrentTimeInString()+".jpg";
			String path = ConfigConstant.IMAGE_SERVER_PATH + fileName;
			saveImageByfloatArray(result[0], path);
			return RenderJsonResult.renderSucessResult(ConfigConstant.IMAGE_SERVER_NAME+fileName);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			return RenderJsonResult.renderErrorResult();
		}
	}
	
	public float[][][][] getResultByContentAndStylePath(
			MultipartFile contentFile, 
			String stylePath,
			HttpServletRequest request) throws IOException
	{
		float [][][][] contentInputArray = getFloatArrayByMultipartFile(contentFile);
		float [][][][] styleInputArray = getFloatArrayByStylePath(stylePath,request);		
		float [][][][] result = getResultByContentAndStyleArray(contentInputArray, styleInputArray);
		return result;
	}

	public float[][][][] getResultByContentAndStyle(
			MultipartFile contentFile,
			MultipartFile styleFile,
			HttpServletRequest request) throws IOException
	{
		float [][][][] contentInputArray = getFloatArrayByMultipartFile(contentFile);
		float [][][][] styleInputArray = getFloatArrayByMultipartFile(styleFile);
		float [][][][] result = getResultByContentAndStyleArray(contentInputArray, styleInputArray);
		return result;
	}
	
	private float[][][][] getResultByContentAndStyleArray(float[][][][]content,float[][][][]style)
	{
		TensorflowModel tensorflowModel = new TensorflowModel(content,style);
		return tensorflowModel.createResult();
	}
	
	private float[][][][] getFloatArrayByMultipartFile(MultipartFile file) throws IOException
	{
		BufferedImage imageIOobject = ImageIO.read(file.getInputStream());
		return getfloatArrayByBufferedImage(imageIOobject);
	}
	
	private float [][][][] getFloatArrayByStylePath(String path,HttpServletRequest request) throws IOException
	{
		InputStream inputStream = request.getServletContext().getResourceAsStream(path);
		BufferedImage imageIOobject = ImageIO.read(inputStream);
		return getfloatArrayByBufferedImage(imageIOobject);
	}
	
	private float[][][][] getfloatArrayByBufferedImage(BufferedImage bufferedImage) throws IOException
	{
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		float[][][][] imagePixel = new float[1][height][width][3];
		int minx = bufferedImage.getMinX();
		int miny = bufferedImage.getMinY();
		for (int x = minx; x < width; x++) 
		{
			for (int y = miny; y < height; y++) 
			{
				int pixel = bufferedImage.getRGB(x, y);
				int rgb0 = (pixel & 0xff0000) >> 16;
				int rgb1 = (pixel & 0xff00) >> 8;
				int rgb2 = (pixel & 0xff);
				 //System.out.println(rgb0 + "," + rgb1 + "," + rgb2);
				
				imagePixel[0][y][x][0] = (float)rgb0;
				imagePixel[0][y][x][1] = (float)rgb1;
				imagePixel[0][y][x][2] = (float)rgb2;
				//if(y==0)
				//	System.out.println("("+y+","+x+") :"+imagePixel[0][y][x][0]+","+imagePixel[0][y][x][1]+","+imagePixel[0][y][x][2]);
			}
		}
		
		return imagePixel;
	}
	
	private void saveImageByfloatArray(float [][][]image,String path) throws IOException
	{	
    	BufferedImage bufferedImage = new BufferedImage(image[0].length,image.length
                , BufferedImage.TYPE_INT_RGB); 
    	int rgb = 0;
    	for(int i = 0; i < image[0].length; i++)
    	{
    		for(int j = 0; j < image.length; j++)
    		{
    			//这里将r、g、b再转化为rgb值，因为bufferedImage没有提供设置单个颜色的方法，
    			//只能设置rgb。rgb最大为8388608，当大于这个值时，应减去255*255*255即16777216 
    			//rgb = (image[i][j][0] * 256 + image[i][j][1]) * 256 + image[i][j][2];  
    			rgb = (turnNormalizedfloatResultToInt(image[j][i][0])<<16) 
    					| (turnNormalizedfloatResultToInt(image[j][i][1])<<8) 
    					| (turnNormalizedfloatResultToInt(image[j][i][2]));
                if(rgb > 8388608)  
                {  
                    rgb = rgb - 16777216;  
                }
                //将rgb值写回图片
    			bufferedImage.setRGB(i, j, rgb);
    		}
    	}
        ImageIO.write(bufferedImage, "jpg",  new File(path));       
	}
	
	/*private float getNormalizeColorByInteger(int element)
	{
		return (float)element/ConfigConstant.NORLIMAZE_SCALE;
	}*/
	
	private int turnNormalizedfloatResultToInt(float element)
	{
		return (int)(element * ConfigConstant.NORLIMAZE_SCALE+ 0.5);
	}
}

