# demo – 後端 API PROJECT #

## 專案結構 ##

在開發工具中以 Maven Project 將專案載入
* **src/main/java/com/demo**
* **aop/** – 權限、log
* **api/** – 主要功能api
  * **controller/** – 流程控制
  * **service/** – 邏輯處理
  * **model/**
    * **req/** – 請求物件
    * **res/** – 回覆物件
* **comp/** – 工具元件
* **config/** – 項目配置
* **constant/** – 常數、列舉
* **controller/** – 共用流程控制、healthyCheck
* **domain/** – 對象模組
* **entity/** – DB ORM
* **error/** – 自訂錯誤、全域異常處理器
* **externalapi/** – 外部系統api
* **filter/** – servlet filter
* **interceptor/** – 攔截器
* **repository/** – dao
* **service/** – 共用邏輯處理
* **util/** – 共用工具
* **validator/** – 欄位檢核
* **src/main/resource**
* **application.yml** – spring 設定檔

## 開發API ##
1. 依照 api spec 建立 request response
2. 建立 controller 並且 extends BaseController
3. 建立 service 執行業務邏輯

## 開發 ##
1. 錯誤處理統一由 ExceptionHandler 回覆錯誤
2. 錯誤直接 throw DemoException 
3. 除邏輯需求，不做 catch Exception 處理

## validator ##
1. 使用 Spring Validation 做為欄位驗證
2. 需自定義驗證可自行開發
3. 如需兩個欄位以上驗證，建議在controller內驗證

## lombok ##
1. @Slf4j 掛載後可直接使用 log 紀錄
2. 使用在單純 java Bean(get set)，常用 @Builder、@AllArgsConstructor、@NoArgsConstructor、@Data

## swagger ##
1. http://127.0.0.1:8080/demo/swagger-ui/index.html
2. 在右上 Authorize 註冊 token ex: Bearer accessToken

## spring data ##
1. 如需要回傳自行定義物件，使用 interface 方式處理，不使用新建立 @Entity 物件  
ex:  
   public interface TestResult {  
        public String getName();  
        public int getTotal();  
   }
2. join等table互相關聯方式，不使用"oneToMany"等tag，使用先查詢在後續查詢方式
3. dynamic 動態查詢條件處理使用 sql 方式處理，或是組合各種情況
ex:  
   @Query(value = "select * " +  
                  "from test " +  
                  "where (:name is null or name = :name)", nativeQuery = true)  
   public List<Test> queryDynamic(@Param("name") String name);
***