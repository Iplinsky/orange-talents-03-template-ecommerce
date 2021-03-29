package br.com.zupacademy.mercadolivre.utils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Primary
public class FalsoUpload {

	public Set<String> envia(List<MultipartFile> listaImagens) {
		return listaImagens.stream().map(imagem -> "http://bucket.io/" + imagem.getOriginalFilename()).collect(Collectors.toSet());
	}

}
