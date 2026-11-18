# GraphQL Introduction / Giới thiệu về GraphQL

**Last Updated / Cập nhật lần cuối:** 28 Oct, 2026

---

## 1. Overview / Tổng quan

**EN:**
GraphQL is an open-source query language for APIs and a runtime for executing queries against a defined schema. It allows clients to request specific fields and related data in a single operation.
- **Flexible Data Fetching:** Clients specify the data they need.
- **Strongly Typed Schema:** Defines the data types and operations available through the API.
- **Multiple Operations:** Queries retrieve data, mutations modify data, and subscriptions support real-time updates when implemented.

**VI:**
GraphQL là một ngôn ngữ truy vấn mã nguồn mở dành cho API và là một môi trường thực thi (runtime) các truy vấn dựa trên một lược đồ (schema) đã được định nghĩa sẵn. Nó cho phép phía client yêu cầu chính xác các trường dữ liệu cụ thể và dữ liệu liên quan chỉ trong một lần thực thi duy nhất.
- **Lấy dữ liệu linh hoạt (Flexible Data Fetching):** Client tự chỉ định chính xác dữ liệu mà họ cần.
- **Lược đồ định kiểu chặt chẽ (Strongly Typed Schema):** Định nghĩa rõ các kiểu dữ liệu và các thao tác khả dụng thông qua API.
- **Đa dạng thao tác (Multiple Operations):** `Queries` dùng để truy xuất dữ liệu, `Mutations` dùng để chỉnh sửa dữ liệu, và `Subscriptions` hỗ trợ cập nhật dữ liệu theo thời gian thực (khi được triển khai).

---

## 2. Components of GraphQL / Các thành phần của GraphQL

**EN:**
GraphQL uses schemas, types, and root operation types to define API data and the operations clients can perform.
- **Schema:** Defines available types, fields, relationships, and operations using Schema Definition Language (SDL). `Query` is required, while `Mutation` and `Subscription` are optional root operation types.
- **Types:** Define data structures and values, including scalar types, object types, enums, interfaces, unions, and input objects.
- **Queries:** Retrieve data from a GraphQL server. Clients specify the fields they need, including related data exposed by the schema.
- **Mutations:** Perform operations that modify data, such as creating, updating, or deleting records. Their behavior depends on the server implementation.

**VI:**
GraphQL sử dụng các schema, kiểu dữ liệu (types) và các kiểu thao tác gốc (root operation types) để định nghĩa dữ liệu API cũng như các thao tác mà client có thể thực hiện.
- **Schema (Lược đồ):** Định nghĩa các kiểu dữ liệu, trường (fields), mối quan hệ và các thao tác khả dụng bằng Ngôn ngữ Định nghĩa Lược đồ (SDL). `Query` là thành phần bắt buộc, trong khi `Mutation` và `Subscription` là các kiểu thao tác gốc tùy chọn.
- **Types (Kiểu dữ liệu):** Định nghĩa cấu trúc dữ liệu và giá trị, bao gồm: kiểu vô hướng (scalar types), kiểu đối tượng (object types), kiểu liệt kê (enums), giao diện (interfaces), kiểu hợp nhất (unions), và đối tượng đầu vào (input objects).
- **Queries (Truy vấn):** Truy xuất dữ liệu từ máy chủ GraphQL. Client chỉ định các trường họ cần, bao gồm cả dữ liệu liên quan được schema cung cấp.
- **Mutations (Biến đổi):** Thực hiện các thao tác làm thay đổi dữ liệu như tạo mới, cập nhật hoặc xóa bản ghi. Cách thức hoạt động thực tế phụ thuộc vào cách triển khai ở phía máy chủ.

---

## 3. GraphQL Query Example / Ví dụ về Truy vấn GraphQL

**EN:**
Consider a blog API where a client needs a post and its author's information. With REST, this might require multiple requests if the API exposes the post and author through separate endpoints. GraphQL can retrieve both in one query when the schema defines the required relationship.

**VI:**
Hãy xem xét một API cho blog, nơi client cần lấy thông tin của một bài viết kèm theo thông tin tác giả của nó. Với REST API, việc này có thể tốn nhiều yêu cầu (requests) nếu bài viết và tác giả nằm ở các endpoint khác nhau. GraphQL có thể lấy cả hai chỉ trong một truy vấn duy nhất khi schema đã định nghĩa sẵn mối quan hệ này.

### Query / Truy vấn:
```graphql
query {
  post(id: "1") {
    title
    content
    author {
      name
      email
    }
  }
}
```

### Response / Phản hồi:
```json
{
  "data": {
    "post": {
      "title": "Introduction to GraphQL",
      "content": "GraphQL is a query language for APIs...",
      "author": {
        "name": "Alice Smith",
        "email": "alice@example.com"
      }
    }
  }
}
```

**EN:**
The query requests the post with ID `"1"`, including its title, content, and author's name and email. The response follows the requested field structure, allowing related data to be retrieved in one operation.

**VI:**
Truy vấn trên yêu cầu bài viết có ID là `"1"`, bao gồm tiêu đề (`title`), nội dung (`content`), cùng với tên (`name`) và email của tác giả (`author`). Kết quả trả về tuân theo đúng cấu trúc trường đã yêu cầu, cho phép lấy toàn bộ dữ liệu liên quan chỉ trong một lần thực thi.

---

## 4. GraphQL Basic Schema Design / Thiết kế Schema GraphQL cơ bản

**EN:**
The following schema defines a simple book API with operations to retrieve, create, update, and delete books.

**VI:**
Đoạn schema dưới đây định nghĩa một API quản lý sách đơn giản với các thao tác truy xuất, tạo mới, cập nhật và xóa sách.

```graphql
type Book {
  id: ID!
  title: String!
  author: String!
}

type Query {
  books: [Book!]!
  book(id: ID!): Book
}

type Mutation {
  createBook(title: String!, author: String!): Book
  updateBook(id: ID!, title: String, author: String): Book
  deleteBook(id: ID!): Boolean
}
```

### Explanation / Giải thích chi tiết:

* **Book Type / Kiểu Book:**
  * **EN:** Defines a book with `id`, `title`, and `author` fields. The `!` symbol indicates that a field cannot return `null`.
  * **VI:** Định nghĩa một cuốn sách với các trường `id`, `title`, và `author`. Ký hiệu `!` biểu thị rằng trường này là bắt buộc và không được phép trả về giá trị `null`.
* **Query Type / Kiểu Query:**
  * **EN:** Defines two data retrieval fields:
    * `books: [Book!]!` returns a non-null list containing non-null `Book` objects.
    * `book(id: ID!): Book` accepts a required ID and returns a book or `null`.
  * **VI:** Định nghĩa hai trường truy xuất dữ liệu:
    * `books: [Book!]!` trả về một danh sách không rỗng (`non-null`) chứa các đối tượng `Book` cũng không được phép `null`.
    * `book(id: ID!): Book` nhận vào một `ID` bắt buộc và trả về một cuốn sách hoặc `null` (nếu không tìm thấy).
* **Mutation Type / Kiểu Mutation:**
  * **EN:** Defines operations to create, update, and delete books. The `createBook` field accepts a `title` and `author`, `updateBook` accepts a book `ID` and optional `title` and `author` arguments, and `deleteBook` returns a `Boolean` value. The actual behavior depends on the resolver implementation.
  * **VI:** Định nghĩa các thao tác thêm, sửa, xóa sách. Trường `createBook` nhận vào `title` và `author`; `updateBook` nhận vào `ID` của sách cùng các tham số tùy chọn là `title` và `author`; `deleteBook` trả về giá trị `Boolean` (`true`/`false`). Hành vi xử lý thực tế phụ thuộc vào cách viết hàm `resolver` ở phía server.

---

## 5. Working of GraphQL / Cách thức hoạt động của GraphQL

**EN:**
GraphQL processes client requests through the following steps:
1. **Client Sends Query:** The client sends an operation specifying the required fields.
2. **Query Validation:** The server checks the operation against the GraphQL schema.
3. **Resolver Execution:** Resolvers retrieve or modify data using databases, APIs, or other sources.
4. **Response Generation:** The server organizes the results according to the requested fields and returns a response, commonly in JSON format.

*Note:* If an error occurs during execution, the response can contain both partial data and error details, depending on the error and schema nullability.

**VI:**
GraphQL xử lý các yêu cầu từ phía client thông qua các bước sau:
1. **Client gửi truy vấn (Client Sends Query):** Client gửi một thao tác chỉ định rõ các trường dữ liệu cần thiết.
2. **Xác thực truy vấn (Query Validation):** Máy chủ kiểm tra tính hợp lệ của thao tác dựa trên GraphQL schema.
3. **Thực thi Resolver (Resolver Execution):** Các hàm `resolver` tiến hành truy xuất hoặc chỉnh sửa dữ liệu từ cơ sở dữ liệu, các API khác hoặc các nguồn dữ liệu liên quan.
4. **Tạo phản hồi (Response Generation):** Máy chủ sắp xếp kết quả theo đúng cấu trúc các trường đã yêu cầu và trả về phản hồi, thường ở định dạng JSON.

*Lưu ý:* Nếu có lỗi xảy ra trong quá trình thực thi, phản hồi có thể chứa đồng thời cả một phần dữ liệu lấy được và chi tiết lỗi, tùy thuộc vào loại lỗi và quy tắc `nullability` (cho phép null hay không) của schema.

---

## 6. Features of GraphQL / Các tính năng nổi bật của GraphQL

- **Flexible Queries / Truy vấn linh hoạt:**
  - **EN:** Clients select the fields and related data they need.
  - **VI:** Client tự chọn chính xác các trường và dữ liệu liên quan mà họ cần.
- **Strong Typing / Định kiểu chặt chẽ:**
  - **EN:** The schema defines valid fields, arguments, and return types, helping the server validate operations.
  - **VI:** Schema định nghĩa rõ các trường, tham số và kiểu trả về hợp lệ, giúp máy chủ dễ dàng xác thực các thao tác.
- **Introspection / Tự kiểm tra (Nội suy):**
  - **EN:** When enabled, introspection allows clients and development tools to explore the schema.
  - **VI:** Khi được kích hoạt, tính năng này cho phép client và các công cụ phát triển tự động khám phá cấu trúc của schema.
- **Real-Time Updates / Cập nhật thời gian thực:**
  - **EN:** Subscriptions can deliver updates when supported by the server and its communication setup.
  - **VI:** `Subscriptions` giúp truyền tải dữ liệu cập nhật tức thì khi máy chủ và giao thức kết nối hỗ trợ.
- **API Evolution / Tiến hóa API mượt mà:**
  - **EN:** Fields can be added and existing fields deprecated to help clients migrate without requiring a new API version for every change.
  - **VI:** Có thể dễ dàng thêm trường mới hoặc đánh dấu các trường cũ là lỗi thời (`deprecated`) để giúp client nâng cấp dần mà không cần tạo phiên bản API mới (v1, v2...) cho mỗi lần thay đổi.
- **Single Endpoint Pattern / Mô hình một Endpoint duy nhất:**
  - **EN:** GraphQL commonly exposes queries and mutations through one endpoint, although the specification does not require a single endpoint.
  - **VI:** GraphQL thường cung cấp mọi truy vấn và biến đổi thông qua một đường dẫn (endpoint) duy nhất, mặc dù tiêu chuẩn kỹ thuật không bắt buộc điều này.

---

## 7. Advantages of GraphQL / Ưu điểm của GraphQL

- **Reduced Unnecessary Data Transfer / Giảm thiểu truyền tải dữ liệu thừa:**
  - **EN:** Clients can request specific fields rather than receiving every field exposed by a REST endpoint.
  - **VI:** Client chỉ yêu cầu đúng các trường cần thiết thay vì phải nhận toàn bộ dữ liệu mà một REST endpoint trả về (tránh tình trạng *Over-fetching*).
- **Fewer Requests for Related Data / Giảm số lượng Request cho dữ liệu liên quan:**
  - **EN:** A single operation can retrieve related data, depending on the schema and server implementation.
  - **VI:** Chỉ một thao tác duy nhất có thể lấy được nhiều dữ liệu có liên quan với nhau (tránh tình trạng *Under-fetching*), tùy thuộc vào thiết kế schema và cách triển khai server.
- **Clear API Structure / Cấu trúc API rõ ràng:**
  - **EN:** A typed schema documents available operations and data relationships.
  - **VI:** Schema định kiểu rõ ràng đóng vai trò như một tài liệu chuẩn xác mô tả các thao tác khả dụng và mối quan hệ dữ liệu.
- **Improved Development Workflow / Cải thiện quy trình phát triển:**
  - **EN:** Introspection and compatible development tools help developers explore and test APIs.
  - **VI:** Tính năng introspection cùng các công cụ hỗ trợ (như GraphiQL, Apollo Studio) giúp lập trình viên dễ dàng khám phá và kiểm thử API.
- **Flexible API Changes / Thay đổi API linh hoạt:**
  - **EN:** Adding fields and deprecating older ones can support backward-compatible evolution when existing behavior is preserved.
  - **VI:** Việc thêm trường mới và đánh dấu trường cũ lỗi thời giúp duy trì khả năng tương thích ngược khi các hành vi cũ vẫn được bảo toàn.