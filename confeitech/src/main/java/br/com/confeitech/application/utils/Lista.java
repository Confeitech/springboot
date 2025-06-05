package br.com.confeitech.application.utils;

public class Lista<T>{

    private T[] vetor;

    private int nroElem;

    public Lista(int tam) {
        this.vetor = (T[]) new Object[tam];
        this.nroElem = 0;
    }

    public void adiciona(T elemento) {
        if (nroElem >= vetor.length) {
            throw new IllegalStateException("Falha ao adicionar!");
        }
        vetor[nroElem++] = elemento;
    }

    public int busca(T elementoProcurado) {
        for (int i = 0; i < nroElem; i++) {
            if (vetor[i] == elementoProcurado) {
                return i;
            }
        }
        return -1;
    }

    public boolean removePeloIndice(int indice) {
        if (indice < 0 || indice >= nroElem) {
            return false;
        }
        for (int i = indice; i < nroElem - 1; i++) {
            vetor[i] = vetor[i + 1];
        }
        nroElem--;
        return true;
    }

    public boolean removeElemento(T elemento) {
        return removePeloIndice(busca(elemento));
    }

    public void exibe() {
        if (nroElem == 0) {
            System.out.println("Lista está vazia.");
        } else {
            for (int i = 0; i < nroElem; i++) {
                System.out.printf(vetor[i] + " ");
            }
            System.out.println();
        }
    }

    public int getTamanho() {
        return nroElem;
    }

    public T[] getVetor() {
        return vetor;
    }

    public T getElemento(int indice){
        if (indice < 0 || indice >= nroElem){
            return null;
        }
        return vetor[indice];
    }
}

