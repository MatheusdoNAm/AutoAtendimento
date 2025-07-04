import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class Stock {
    private Map<Integer, ProductStock> stockMap;
    // Define o nome do arquivo CSV
    private static final String CSV_FILE = "stock.csv";
    // Define o formatador de data
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Formato ISO para CSV

    public Stock() {
        this.stockMap = new HashMap<>();
        loadFromCsv(); // Carrega o estoque do CSV ao inicializar
    }

    /**
     * Carrega os dados do estoque de um arquivo CSV.
     * Cada linha do CSV deve ter o formato: codigo,nome,preco,dataValidade(AAAA-MM-DD),quantidadeEmEstoque
     * Se a data de validade não existir, deve ser uma string vazia.
     */
    public void loadFromCsv() {
        stockMap.clear(); // Limpa o estoque atual antes de carregar
        File file = new File(CSV_FILE);
        if (!file.exists() || file.length() == 0) {
            System.out.println("Arquivo CSV de estoque não encontrado ou vazio. Iniciando com estoque vazio.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) { // Pelo menos código, nome, preço, quantidade (expDate é opcional)
                    try {
                        int code = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        double price = Double.parseDouble(parts[2].trim());
                        int amountStock = Integer.parseInt(parts[3].trim()); // Corrigido para ler amountStock

                        LocalDate expDate = null;
                        if (parts.length >= 5 && !parts[4].trim().isEmpty() && !parts[4].trim().equalsIgnoreCase("N/A")) {
                            try {
                                expDate = LocalDate.parse(parts[4].trim(), DATE_FORMATTER);
                            } catch (DateTimeParseException e) {
                                System.err.println("Erro ao parsear data de validade para o produto " + name + ": " + parts[4] + ". Será ignorada.");
                            }
                        }

                        Product product;
                        if (expDate != null) {
                            product = new Product(code, name, price, expDate);
                        } else {
                            product = new Product(code, name, price);
                        }
                        ProductStock productStock = new ProductStock(product, amountStock);
                        stockMap.put(code, productStock);
                    } catch (NumberFormatException e) {
                        System.err.println("Erro de formato de número em linha CSV: " + line + ". Linha ignorada.");
                    }
                } else {
                    System.err.println("Formato de linha CSV inválido: " + line + ". Linha ignorada.");
                }
            }
            System.out.println("Estoque carregado com sucesso do CSV.");
        } catch (IOException e) {
            System.err.println("Erro ao carregar estoque do CSV: " + e.getMessage());
        }
    }

    /**
     * Salva os dados do estoque em um arquivo CSV.
     */
    public void saveToCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (ProductStock productStock : stockMap.values()) {
                Product product = productStock.getProduct();
                String expDateStr = (product.getExpDate() != null) ? product.getExpDate().format(DATE_FORMATTER) : ""; // Se não tiver data, string vazia
                // Formato: codigo,nome,preco,quantidadeEmEstoque,dataValidade
                bw.write(String.format("%d,%s,%.2f,%d,%s%n",
                                        product.getCode(),
                                        product.getName(),
                                        product.getPrice(),
                                        productStock.getAmountStock(),
                                        expDateStr));
            }
            System.out.println("Estoque salvo com sucesso para o CSV.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar estoque para o CSV: " + e.getMessage());
        }
    }

    /**
     * Cadastra um novo produto no estoque com quantidade inicial zero, usando um código fornecido.
     * Após o registro, salva o estoque no CSV.
     * @param code Código único do produto (definido pelo administrador).
     * @param name Nome do produto.
     * @param price Preço do produto.
     * @return O objeto Product recém-cadastrado.
     * @throws IllegalArgumentException se o código ou nome já existirem.
     */
    public Product registerProduct(int code, String name, double price) {
        // Valida se o código já existe
        if (stockMap.containsKey(code)) {
            throw new IllegalArgumentException("Product with code " + code + " already exists.");
        }

        // Valida se o nome já existe
        for (ProductStock ps : stockMap.values()) {
            if (ps.getProduct().getName().equalsIgnoreCase(name)) {
                throw new IllegalArgumentException("Product with name '" + name + "' already exists.");
            }
        }

        Product newProduct = new Product(code, name, price);
        ProductStock newProductStock = new ProductStock(newProduct, 0); // Estoque inicial zerado
        stockMap.put(code, newProductStock);
        saveToCsv(); // Salva após o registro
        System.out.println("Product '" + name + "' registered with code " + code + " and 0 units in stock.");
        return newProduct;
    }

    public void deleteProduct(int productCode) {
        ProductStock itemInStock = locateProductInStock(productCode);

        if (itemInStock == null) {
            throw new IllegalArgumentException("Product with code " + productCode + " not found in stock.");
        }
        stockMap.remove(productCode);
        saveToCsv(); // Salva após a exclusão
        System.out.println("Product with code " + productCode + " removed from de system.");
    }

    /**
     * Cadastra um novo produto com data de validade no estoque com quantidade inicial zero, usando um código fornecido.
     * Após o registro, salva o estoque no CSV.
     * @param code Código único do produto (definido pelo administrador).
     * @param name Nome do produto.
     * @param price Preço do produto.
     * @param expDate Data de validade do produto.
     * @return O objeto Product recém-cadastrado.
     * @throws IllegalArgumentException se o código ou nome já existirem.
     */
    public Product registerProduct(int code, String name, double price, LocalDate expDate) {
        // Valida se o código já existe
        if (stockMap.containsKey(code)){
            throw new IllegalArgumentException("Product with code " + code + " already exists.");
        }

        // Valida se o nome já existe
        for (ProductStock ps : stockMap.values()) {
            if (ps.getProduct().getName().equalsIgnoreCase(name)) {
                throw new IllegalArgumentException("Product with name '" + name + "' already exists.");
            }
        }

        Product newProduct = new Product(code, name, price, expDate);
        ProductStock newProductStock = new ProductStock(newProduct, 0); // Estoque inicial zerado
        stockMap.put(code, newProductStock);
        saveToCsv(); // Salva após o registro
        System.out.println("Check");
        System.out.println("Product '" + name + "' registered with code " + code + " and 0 units in stock.");
        return newProduct;
    }

    /**
     * Localiza um ProductStock pelo código do produto.
     * @param code Código do produto.
     * @return O ProductStock correspondente ou null se não for encontrado.
     */
    public ProductStock locateProductInStock(int code) {
        return stockMap.get(code);
    }

    /**
     * Adiciona uma quantidade ao estoque de um produto existente.
     * Após a adição, salva o estoque no CSV.
     * @param productCode Código do produto.
     * @param quantity Quantidade a ser adicionada.
     * @throws IllegalArgumentException se o produto não for encontrado ou a quantidade for inválida.
     */
    public void addStock(int productCode, int quantity) {
        ProductStock itemInStock = locateProductInStock(productCode);

        if (itemInStock == null) {
            throw new IllegalArgumentException("Product with code " + productCode + " not found in stock. Please register it first.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity to add must be positive. Value received: " + quantity);
        }

        itemInStock.addStock(quantity);
        saveToCsv(); // Salva após a adição
        System.out.println("Quantity of " + itemInStock.getProduct().getName() + " updated. New quantity: " + itemInStock.getAmountStock() + " units.");
    }

    /**
     * Remove uma quantidade do estoque de um produto existente.
     * Se a quantidade em estoque chegar a zero, o produto é removido do mapa de estoque.
     * Após a remoção, salva o estoque no CSV.
     * @param productCode Código do produto.
     * @param quantity Quantidade a ser removida.
     * @throws IllegalArgumentException se o produto não for encontrado.
     */
    public void removeStock(int productCode, int quantity) {
        ProductStock itemInStock = locateProductInStock(productCode);

        if (itemInStock == null) {
            throw new IllegalArgumentException("Product with code " + productCode + " not found in stock.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity to remove must be positive. Value received: " + quantity);
        }

        if (itemInStock.removeStock(quantity)) {
            System.out.println(quantity + " units of " + itemInStock.getProduct().getName() + " have been removed from inventory. " + itemInStock.getAmountStock() + " units remain.");

            if (itemInStock.getAmountStock() == 0) {
                System.out.println(itemInStock.getProduct().getName() + " is now out of stock.");
            }
            saveToCsv(); // Salva após a remoção bem-sucedida
        } else {
            System.out.println("Insufficient stock of " + itemInStock.getProduct().getName() + ". Available: " + itemInStock.getAmountStock() + " units.");
        }
    }

    /**
     * Obtém a quantidade disponível de um produto.
     * @param productCode Código do produto.
     * @return A quantidade em estoque, ou 0 se o produto não for encontrado.
     */
    public int getQuantityAvailable(int productCode) {
        ProductStock itemInStock = locateProductInStock(productCode);
        return (itemInStock != null) ? itemInStock.getAmountStock() : 0;
    }

    /**
     * Lista todos os produtos atualmente no estoque.
     */
    public void listStock() {
        if (stockMap.isEmpty()) {
            System.out.println("Empty stock");
            return;
        }
        System.out.println("\nCurrent stock\n");
        for (ProductStock productInStock : stockMap.values()) {
            Product product = productInStock.getProduct();
            String expDateStr = (product.getExpDate() != null) ? " (Exp. Date: " + product.getExpDate().format(DATE_FORMATTER) + ")" : "";
            System.out.println("   " + product.getCode() + " - " + product.getName() + ": " + productInStock.getAmountStock() + " units in stock" + expDateStr + ".");
        }
        System.out.println("---------------------\n");
    }
}