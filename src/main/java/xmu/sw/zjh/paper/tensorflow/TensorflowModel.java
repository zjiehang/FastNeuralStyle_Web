package xmu.sw.zjh.paper.tensorflow;

import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

@SuppressWarnings("rawtypes")
public class TensorflowModel 
{
	private final String MODEL_DIR = getClass().getResource("/edsr").toString().replace("file:", "");
	private final String MODEL_TAG_CONSTANT_TYPE = "serve";
	
	private Tensor content;
	private Tensor style;
	private int width;
	private int height;
	private int channel;
	
	public TensorflowModel()
	{
		this.content = null;
		this.style = null;
		this.width = 0;
		this.height = 0;
		this.channel = 3;
	}
	
	public TensorflowModel(float[][][][]content,float[][][][]style)
	{
		this.content = Tensor.create(content);
		this.style = Tensor.create(style);
		this.height = content[0].length;
		this.width = content[0][1].length;
		this.channel = content[0][1][2].length;
	}
	
	public float[][][][] createResult() 
	{
		SavedModelBundle bundle = SavedModelBundle.load(MODEL_DIR,MODEL_TAG_CONSTANT_TYPE);
		Session session = bundle.session();
		Tensor encoderContentOutput = session.runner().feed("image", content).fetch("encoder-output").run().get(0);
		Tensor encoderStyleOutput = session.runner().feed("image", style).fetch("encoder-output").run().get(0);
		float [][][][] result = session.runner().feed("adain-content-input", encoderContentOutput).feed("adain-style-input",encoderStyleOutput).fetch("output").run().get(0).copyTo(new float[1][height][width][channel]);
		session.close();
		bundle.close();
		return result;
	}
}
