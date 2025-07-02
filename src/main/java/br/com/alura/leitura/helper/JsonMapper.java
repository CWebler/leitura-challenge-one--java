package br.com.alura.leitura.helper;

import br.com.alura.leitura.model.RespostaApi;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static RespostaApi parseJsonParaObjeto(String json) throws Exception {
        return mapper.readValue(json, RespostaApi.class);
    }
}
