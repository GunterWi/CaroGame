# Caro-Game-Online

### Dự án thực tập

## Giới thiệu

Game Caro Online dùng MultiThread và Socket
Hiện tại game và giao diện khá là đơn giản.

### Các tính năng
- Kết nối giữa server và client
- Sử dụng Multithread trong java để quản lý nhiều client
- Đăng nhập, đăng ký
- Tìm phòng
- Xem hồ sơ
- Đánh caro, Kiểm tra thắng thua.
- Chat khi đang chơi
## Cài đặt

Hướng dẫn setup:

**Bên Server**

- File Server [https://github.com/GunterWi/ServerCaro](https://github.com/GunterWi/ServerCaro)
  
- Cài SQL ( hiện tại mình đang dùng SQLServer) và IDE là Netbean 17
- Thêm thư viện [lib](lib) (Thêm thư viện KGradientPanel )
- Thêm cơ sở dữ liệu [src/database/Caro_SQL.sql](CaroServer/src/database/Caro_SQL.sql) ( port mặc định sql là 1433, mặc định tên database là Account)
- Chạy Server [src/controller/RunServer.java](CaroServer/src/controller/RunServer.java)

**Bên Client**
- File Client [https://github.com/GunterWi/ClientCaro](https://github.com/GunterWi/ClientCaro)

- Vào SocketHandler [src/controller/SocketHandler.java](CaroClient/src/controller/SocketHandler.java) (đổi IP lại)
- Chạy Client [src/controller/RunClient.java](CaroClient/src/controller/RunClient.java)

