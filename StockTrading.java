import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Stock {
    String symbol;
    double price;
    int quantity;

    Stock(String symbol, double price, int quantity) {
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
    }
}

class Portfolio {
    Map<String, Stock> stocks = new HashMap<>();
    double balance;

    Portfolio(double balance) {
        this.balance = balance;
    }

    void buyStock(String symbol, double price, int quantity) {
        if (balance >= price * quantity) {
            balance -= price * quantity;
            if (stocks.containsKey(symbol)) {
                stocks.get(symbol).quantity += quantity;
            } else {
                stocks.put(symbol, new Stock(symbol, price, quantity));
            }
            System.out.println("Bought " + quantity + "of " + symbol);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    void sellStock(String symbol, int quantity) {
        if (stocks.containsKey(symbol) && stocks.get(symbol).quantity >= quantity) {
            Stock stock = stocks.get(symbol);
            balance += stock.price * quantity;
            stock.quantity -= quantity;
            if (stock.quantity == 0) {
                stocks.remove(symbol);
            }
            System.out.println("Sold " + quantity + " of " + symbol);
        } else {
            System.out.println("Not enough stocks to sell.");
        }
    }

    void displayPortfolio() {
        System.out.println("Balance: " + balance);
        for (Stock stock : stocks.values()) {
            System.out.println(stock.symbol + ": " + stock.quantity + " @ " + stock.price);
        }
    }
}

public class StockTrading {
    public static void main(String[] args) {
        Portfolio portfolio = new Portfolio(10000);
        Map<String, Double> marketData = new HashMap<>();
        marketData.put("RELIANCE", 2500.0);
        marketData.put("INFY", 1500.0);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1.Display Market Data");
            System.out.println("2.Buy Stock");
            System.out.println("\3.Sell Stock");
            System.out.println("4.Display Portfolio");
            System.out.println("5.Exit");
            System.out.print("Choose:");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Market Data:");
                    for (Map.Entry<String, Double> entry : marketData.entrySet()) {
                        System.out.println(entry.getKey() + ":" + entry.getValue());
                    }
                    break;
                case 2:
                    System.out.print("Enter stock symbol:");
                    String buySymbol = scanner.nextLine();
                    if (marketData.containsKey(buySymbol)) {
                        System.out.print("Enter quantity:");
                        int buyQuantity = scanner.nextInt();
                        scanner.nextLine();
                        portfolio.buyStock(buySymbol, marketData.get(buySymbol), buyQuantity);
                    } else {
                        System.out.println("Ypu don't own this stock.");
                    }
                    break;
                case 3:
                    System.out.print("Enter stock symbol: ");
                    String sellSymbol = scanner.nextLine();
                    if (portfolio.stocks.containsKey(sellSymbol)) {
                        System.out.print("Enter quantity:");
                        int sellQuantity = scanner.nextInt();
                        scanner.nextLine();
                        portfolio.sellStock(sellSymbol, sellQuantity);
                    } else {
                        System.out.println("You don't own this stock.");
                    }
                    break;
                case 4:
                    portfolio.displayPortfolio();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}