import java.io.IOException;
import java.net.InetAddress;

public class NetworkLatency {
    public static void main(String[] args) {
        String host = "8.8.8.8"; // Замените на нужный вам сервер или IP-адрес
        int numberOfPings = 10; // Количество пакетов для отправки

        try {
            measureLatency(host, numberOfPings);
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static void measureLatency(String host, int numberOfPings) throws IOException {
        InetAddress address = InetAddress.getByName(host);
        long totalTime = 0;
        int successCount = 0;

        System.out.println("Пинг " + host + " (" + address.getHostAddress() + ") с использованием ICMP...");

        for (int i = 1; i <= numberOfPings; i++) {
            long startTime = System.nanoTime(); // Время отправки
            boolean reachable = address.isReachable(1000); // Тайм-аут 1 секунда
            long endTime = System.nanoTime(); // Время получения ответа

            if (reachable) {
                long latency = (endTime - startTime) / 1_000_000; // Конвертация в миллисекунды
                System.out.println("Ответ от " + host + ": Время=" + latency + "ms");
                totalTime += latency;
                successCount++;
            } else {
                System.out.println("Нет ответа от " + host);
            }
        }

        if (successCount > 0) {
            double averageLatency = totalTime / (double) successCount;
            System.out.println("\nСреднее время отклика: " + averageLatency + "ms");
        } else {
            System.out.println("\nВсе попытки пинга завершились неудачно.");
        }
    }
}
