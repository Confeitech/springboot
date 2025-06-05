package br.com.confeitech.application.utils;

import java.util.List;

public interface Ordenacao<T> {

    List<T> ordenarListaEmOrdemAlfabetica(List<T> listaDesordenada, int indMenor, int indMaior);
}
