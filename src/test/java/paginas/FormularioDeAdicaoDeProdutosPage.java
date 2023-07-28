package paginas;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FormularioDeAdicaoDeProdutosPage {
    private WebDriver navegador;

    public FormularioDeAdicaoDeProdutosPage(WebDriver navegador) {
        this.navegador = navegador;
    }

    public FormularioDeAdicaoDeProdutosPage informarNomeDoProduto(String produtoNome) {
        navegador.findElement(By.id("produtonome")).sendKeys(produtoNome);

        return this;
    }

    public FormularioDeAdicaoDeProdutosPage informarValorDoProduto(String produtoValor) {
        navegador.findElement(By.name("produtovalor")).sendKeys(produtoValor);

        return this;
    }
    public FormularioDeAdicaoDeProdutosPage informarCoresDoProduto (String produtoCores) {
        navegador.findElement(By.id("produtocores")).sendKeys(produtoCores);

        return this;
    }
    public ListaDeProdutosPage submeterFormularioDeAdicaoComErro() {
        navegador.findElement(By.cssSelector("button[type='submit']")).click();

        return new ListaDeProdutosPage(navegador);
    }
    public FormularioDeEdicaoDeProdutoPage submeterFormularioDeAdicaoComSucesso() {
        navegador.findElement(By.cssSelector("button[type='submit']")).click();

        return new FormularioDeEdicaoDeProdutoPage(navegador);
    }
}
