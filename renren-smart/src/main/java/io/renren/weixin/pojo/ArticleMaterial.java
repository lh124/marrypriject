package io.renren.weixin.pojo;

/**
 * ͼ���ز�
 * 
 * @author liufeng
 * @date 2014-11-20
 */
public class ArticleMaterial {
	// ͼ����Ϣ����ͼ��media_id
	private String thumb_media_id;
	// ͼ����Ϣ������
	private String author;
	// ͼ����Ϣ�ı���
	private String title;
	// ͼ����Ϣ���Ķ�ԭ�ġ���ҳ��
	private String content_source_url;
	// ͼ����Ϣҳ�������
	private String content;
	// ͼ����Ϣ������
	private String digest;
	// �Ƿ���ʾ����ͼƬ��1����ʾ 0������ʾ��
	private String show_cover_pic;

	public String getThumb_media_id() {
		return thumb_media_id;
	}

	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent_source_url() {
		return content_source_url;
	}

	public void setContent_source_url(String content_source_url) {
		this.content_source_url = content_source_url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getShow_cover_pic() {
		return show_cover_pic;
	}

	public void setShow_cover_pic(String show_cover_pic) {
		this.show_cover_pic = show_cover_pic;
	}
}
