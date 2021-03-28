package br.com.zupacademy.mercadolivre.models.form.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.sun.istack.NotNull;

public class ImagemFormDto {

	@Size(min = 1)
	@NotNull
	private List<MultipartFile> listaImagens = new ArrayList<MultipartFile>();

	public ImagemFormDto(@Size(min = 1) @NotNull List<MultipartFile> listaImagens) {
		Assert.isTrue(listaImagens.size() > 0, "O produto preciso ter ao menos uma imagem associada!");
		this.listaImagens = listaImagens;
	}

	public List<MultipartFile> getListaImagens() {
		return listaImagens;
	}

	public void setListaImagens(List<MultipartFile> listaImagens) {
		this.listaImagens = listaImagens;
	}

}
