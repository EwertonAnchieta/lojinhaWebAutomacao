package modulos.produtos;

import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.v85.log.Log;
import paginas.LoginPage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

@DisplayName("Testes Web no Modulo de Produtos")
public class ProdutosTest {

    private WebDriver navegador;
    private String baseUrl;
    private String username;
    private String password;

    @BeforeEach
    public void beforeEach() {
        {
            try (InputStream input = new FileInputStream("C:\\Users\\ewert\\IdeaProjects\\config.properties")) {

                Properties prop = new Properties();
                prop.load(input);
                this.baseUrl = prop.getProperty("db.url");
                this.username = prop.getProperty("db.user");
                this.password = prop.getProperty("db.password");
                this.navegador = new ChromeDriver();
                this.navegador.manage().window().maximize();
                this.navegador.manage().timeouts().implicitlyWait((Duration.ofSeconds(5)));
                this.navegador.get(baseUrl);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
      }

    @Test
    @DisplayName("Não é permitido registrar um Produto com valor igual a zero")
    public void testNaoEPermitidoRegistrarProdutoComValorIgualAZero() {

        // Fazer login
        String mensagemApresentada = new LoginPage(navegador)
                .informarOUsuario(username)
                .informarASenha(password)
                .submeterFormularioDeLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeDoProduto("MacBook Pro")
                .informarValorDoProduto("000")
                .informarCoresDoProduto("preto, branco")
                .submeterFormularioDeAdicaoComErro()
                .capturarMensagemApresentada();


        Assertions.assertEquals("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00", mensagemApresentada);
}

    @Test
    @DisplayName("Não é Permitido registrar um produto com valor maior que 7.000,00")
    public void testNaoEPermitidoRegistrarUmProdutoComValorAcimaDeSeteMil() {
        String mensagemApresentada = new LoginPage(navegador)
                .informarOUsuario(username)
                .informarASenha(password)
                .submeterFormularioDeLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeDoProduto("Iphone")
                .informarValorDoProduto("700001")
                .informarCoresDoProduto("preto, azul")
                .submeterFormularioDeAdicaoComErro()
                .capturarMensagemApresentada();

        Assertions.assertEquals("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00", mensagemApresentada);

    }

    @Test
    @DisplayName("Posso adicionar Produtos que estejam no limite de 0,01")
    public void testPossoAdicionarProdutosComValorDeUmCentavo() {
       String mensagemApresentada = new LoginPage(navegador)
                .informarOUsuario(username)
                .informarASenha(password)
                .submeterFormularioDeLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeDoProduto("Macbook Pro")
                .informarValorDoProduto("001")
                .informarCoresDoProduto("preto")
                .submeterFormularioDeAdicaoComSucesso()
                .capturarMensagemApresentada();

        Assertions.assertEquals("Produto adicionado com sucesso", mensagemApresentada);
    }
    @Test
    @DisplayName("Posso adicionar Produtos que estejam no limite de 7.000,00")
    public void testPossoAdicionarProdutosComValorDeSeteMilReais() {
        String mensagemApresentada = new LoginPage(navegador)
                .informarOUsuario(username)
                .informarASenha(password)
                .submeterFormularioDeLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeDoProduto("MacBook Pro")
                .informarValorDoProduto("700000")
                .informarCoresDoProduto("preto, branco")
                .submeterFormularioDeAdicaoComSucesso()
                .capturarMensagemApresentada();

        Assertions.assertEquals("Produto adicionado com sucesso", mensagemApresentada);
    }

    @AfterEach
    public void aferEach () {
        // Vou fechar o navegador
        navegador.quit();
    }
}
