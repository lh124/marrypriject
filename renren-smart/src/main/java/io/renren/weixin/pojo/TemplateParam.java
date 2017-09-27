package io.renren.weixin.pojo;

/**
 * ģ�����
 * 
 * @author liufeng
 * @date 2014-11-18
 */
public class TemplateParam {
	// �������
	private String name;
	// ����ֵ
	private String value;
	// ��ɫ
	private String color;

	public TemplateParam(String name, String value, String color) {
		this.name = name;
		this.value = value;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
