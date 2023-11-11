# [Usando o serviço Extract And Load Data Batch - Swagger UI:  Passo a passo para execução](https://app.tango.us/app/workflow/7f54464b-5b28-41ec-bfc2-dd3992112ddc?utm_source=markdown&utm_medium=markdown&utm_campaign=workflow%20export%20links)

__Creation Date:__ October 22, 2023  
__Created By:__ Gabriela Claro  
[View most recent version on Tango.us](https://app.tango.us/app/workflow/7f54464b-5b28-41ec-bfc2-dd3992112ddc?utm_source=markdown&utm_medium=markdown&utm_campaign=workflow%20export%20links)



***




## # [Extract And Load Data Batch - Swagger UI](http://localhost:8080/swagger-ui/index.html#/)
Clique no _link_ para acessar a interface de usuário disponível do serviço _"Extract And Load Data Batch"_.

Neste passo a passo, será apresentado o fluxo mínimo necessário para executar o processamento de extração e carregamento de dados entre dois banco de dados distintos usando a interface _"Swagger UI"_ disponibilizada pelo serviço acessível em: [http://localhost:8080/swagger-ui/index.html#](http://localhost:8080/swagger-ui/index.html#).


## # Adicionar Banco de Dados de Origem e Destino


### 1. Clique em "Datasource"

Clique na opção _"Datasource"_ para acessar as funcionalidades de gerenciamento de cadastro de bancos de dados e inserir as propriedades do banco de dados de origem ou destino mantenedoras dos dados a serem extraídos e carregados.

![Step 1 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/1bf2f957-291d-4cf5-83f3-b9294cb138a0/98396eb9-fd2f-43f0-970d-2e1785a6e63e.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=19&mark-y=406&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMTQ5Jmg9NTkmZml0PWNyb3AmY29ybmVyLXJhZGl1cz0xMA%3D%3D)


### 2. Clique na funcionalidade "POST /v1/datasource"

A funcionalidade _"POST /v1/datasource"_ refere-se ao caso de uso: **RF01 – Gerenciar Cadastro de Bancos de Dados – Inserir**.

![Step 2 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/3a60bfa9-33e1-4875-a445-451a9338b204/cb4fda55-15dc-4c86-80d5-dfee33ae4f41.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=24&mark-y=600&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMDg3Jmg9MjkmZml0PWNyb3AmY29ybmVyLXJhZGl1cz0xMA%3D%3D)


### 3. [Opcional] Clique em "Schema"

Esta opção exibe a especificação dos atributos definidos pelo serviço inerentes a funcionalidade a ser executada. Recurso padrão da interface e disponível em todas as funcionalidades.

> _Passo opcional_

![Step 3 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/1af22eb4-3a79-46c7-b344-77312e184bc8/884e5762-3a4a-45be-8880-258d7c7467e6.png?crop=focalpoint&fit=crop&fp-x=0.1119&fp-y=0.4855&fp-z=2.9864&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=339&mark-y=363&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTQlMkNGRjc0NDImdz0xMjUmaD01MyZmaXQ9Y3JvcCZjb3JuZXItcmFkaXVzPTEw)


### 4. [Opcional] Será exibida a especificação dos atributos da requisição

No esquema é possível observar que os elementos marcados com \* são de preenchimento obrigatório, além disso o elemento informa qual o tipo de dado e se há alguma regra de consistência conforme o tipo de dado.

> _Passo opcional_

![Step 4 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/ad0e7cef-c8be-44c2-8714-4cb323f0b5e5/543a981a-ced1-4fcb-8293-4aa4f29cf110.png?crop=focalpoint&fit=crop&fp-x=0.2140&fp-y=0.2839&fp-z=2.0996&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=93&mark-y=254&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTQlMkNGRjc0NDImdz00NDQmaD01NCZmaXQ9Y3JvcCZjb3JuZXItcmFkaXVzPTEw)


### 5. Clique no botão "Try it out"

O botão _"Try it out"_ habilita a área de texto _"Request Body"_ para informar o corpo da requisição em formato _"JSON_", ou seja, informar os atributos de entrada necessários para realizar o cadastro de um novo banco de dados e adicionar a sua conexão ao serviço.

![Step 5 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/5be783bc-801b-49a0-9a28-3ff71cdbc4d6/d58403be-95c8-4b16-89de-4b65bccdfb01.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=1056&mark-y=555&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz05NCZoPTMxJmZpdD1jcm9wJmNvcm5lci1yYWRpdXM9MTA%3D)


### 6. Preencha a área de texto com o corpo da requisição em formato "JSON"

Preencha a área de texto denominada _"Request body"_ com o corpo da requisição em formato _"JSON"_ respeitando a especificação dos atributos definidos pelo serviço, disponível no recurso _"Schema"_ disponível na interface.

Exemplo de propriedades do banco de dados de origem:

```json
{
  "databaseName": "CO_APP",
  "databaseProvider": "Oracle",
  "databasePlatform": "org.hibernate.dialect.Oracle10gDialect",
  "userName": "CO_APP",
  "password": "CO_APP",
  "jdbcConnectionUrl": "jdbc:oracle:thin:@//localhost:49161/xe",
  "driverClassName": "oracle.jdbc.driver.OracleDriver",
  "databaseDialect": "org.hibernate.dialect.Oracle10gDialect",
  "databaseSchema": "CO_APP"
}
```

![Step 6 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/17168a8a-5e17-4f41-8eb7-757995e3c7c6/612274bb-13ed-42e1-9d24-392febe8175f.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=36&mark-y=443&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMTE1Jmg9MjMyJmZpdD1jcm9wJmNvcm5lci1yYWRpdXM9MTA%3D)


### 7. Clique no botão "Execute"

Clique no botão _"Execute"_ para realizar o envio da requisição ao servidor para efetivar o cadastro de um novo banco de dados.

![Step 7 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/bf134361-d1b7-4abf-92b8-11f7b0854921/11f27089-3614-4e7b-acf7-9d465be6e4fe.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=36&mark-y=546&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMTE1Jmg9MzUmZml0PWNyb3AmY29ybmVyLXJhZGl1cz0xMA%3D%3D)


### 8. Após o envio da requisição, o resultado da execução será exibido na área de texto denominada "Response body"

O resultado da requisição informa as propriedades _"Code"_, neste caso _"201 - Created"_ e o _"Response body"_ em notação _"JSON"_ contendo o identificador único do registro criado. Reserve esta informação.

```json
{
  "uid": 21
}
```

![Step 8 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/e0e2b54f-3b2b-40c4-8f7b-cb4184dcdf3f/b98453e8-df2e-4985-8ffa-8004e23c0888.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=104&mark-y=516&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMDQ3Jmg9NjQmZml0PWNyb3AmY29ybmVyLXJhZGl1cz0xMA%3D%3D)


### 9. [Opcional] Ainda na funcionalidade "POST /v1/datasource" clique no botão "Clear"

Clique no botão _"Clear"_ para limpar o resultado da requisição anterior. Recurso padrão da interface e disponível em todas as funcionalidades.

> _Passo opcional._

![Step 9 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/fbd5fe95-dab3-4851-b9c8-9a84157f0c2d/97764f1b-6e59-44e9-9d84-d9767270b47e.png?crop=focalpoint&fit=crop&fp-x=0.6714&fp-y=0.6307&fp-z=1.5197&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=274&mark-y=363&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTQlMkNGRjc0NDImdz04NDAmaD00NiZmaXQ9Y3JvcCZjb3JuZXItcmFkaXVzPTEw)


### 10. [Opcional] Clique no botão "Reset"

Clique no botão _"Reset"_ para remover o corpo da requisição anterior. Recurso padrão da interface e disponível em todas as funcionalidades.

> _Passo opcional._

![Step 10 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/793a6bbc-4fc3-40df-bd86-9562263e2551/bd78e399-5ed0-4dfe-8150-c8c1dfc5cac8.png?crop=focalpoint&fit=crop&fp-x=0.9075&fp-y=0.1126&fp-z=2.9476&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=693&mark-y=213&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTQlMkNGRjc0NDImdz0zNjAmaD05MCZmaXQ9Y3JvcCZjb3JuZXItcmFkaXVzPTEw)


ℹ️ Para adicionar o cadastro do banco de dados de destino, repita os passos 6, 7 e 8, informando suas respectivas propriedades em formato "JSON" na área de texto denominada "Request body", conforme exemplo a seguir:{
"databaseName": "CO_APP_TARGET",
"databaseProvider": "Oracle",
"databasePlatform": "org.hibernate.dialect.Oracle10gDialect",
"userName": "CO_APP_TARGET",
"password": "CO_APP_TARGET",
"jdbcConnectionUrl": "jdbc:oracle:thin:@//localhost:49161/xe",
"driverClassName": "oracle.jdbc.driver.OracleDriver",
"databaseDialect": "org.hibernate.dialect.Oracle10gDialect",
"databaseSchema": "CO_APP_TARGET"
}


## # Cadastrar Tabelas


### 11. Clique em "Tables"

Clique na opção _"Tables"_ para acessar as funcionalidades de gerenciamento de cadastro de tabelas de domínio dos dados a serem extraídos da origem e carregados no destino.

As tabelas de origem e destino não necessariamente precisam ter cadastros distintos. Recomenda-se criar registros separados caso o nome físico destas tabelas seja diferente entre os bancos de dados de origem e destino.

Neste exemplo, o nome da tabela é o mesmo em ambos os bancos de dados, portanto somente um registro é necessário.

![Step 11 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/47e91307-37e1-4556-b5cd-f37c5ec29675/1482ae93-5e56-4e46-8304-92a291002e54.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=19&mark-y=495&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMTQ5Jmg9NTkmZml0PWNyb3AmY29ybmVyLXJhZGl1cz0xMA%3D%3D)


### 12. Clique na funcionalidade "POST /v1/data/tables"

A funcionalidade _"POST /v1/data/tables"_ refere-se ao caso de uso: **RF02 – Gerenciar Cadastro de Tabelas – Inserir**.

![Step 12 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/88338c7f-44c3-4065-98fb-a9c97813afa9/0662bee7-6a3e-4d67-8850-dd56dae07ecb.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=24&mark-y=447&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMDg3Jmg9MjkmZml0PWNyb3AmY29ybmVyLXJhZGl1cz0xMA%3D%3D)


### 13. Clique no botão "Try it out"

O botão _"Try it out"_ habilita a área de texto _"Request Body"_ para informar o corpo da requisição em formato _"JSON"_, ou seja, informar os atributos de entrada necessários para realizar o cadastro de uma nova tabela.

![Step 13 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/aa181c44-9e6c-436e-82f8-5a14707952a6/45bfae07-9e46-4012-8149-c7815feac9c5.png?crop=focalpoint&fit=crop&fp-x=0.8401&fp-y=0.6179&fp-z=2.9485&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=776&mark-y=394&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTQlMkNGRjc0NDImdz0yNzcmaD05MCZmaXQ9Y3JvcCZjb3JuZXItcmFkaXVzPTEw)


### 14. Preencha a área de texto com o corpo da requisição em formato "JSON"

Preencha a área de texto denominada _"Request body"_ com o corpo da requisição em formato _"JSON"_ respeitando a especificação dos atributos definidos pelo serviço, disponível no recurso _"Schema"_ disponível na interface.

Exemplo de cadastro de tabela:

```json
{
  "tablePhysicalName": "CUSTOMERS"
}
```

![Step 14 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/ad5e7cca-c0e9-46fd-8ba7-aba93aee8de5/0fa6aee1-4158-4d32-8739-38121221afd0.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=36&mark-y=612&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMTE1Jmg9MTYzJmZpdD1jcm9wJmNvcm5lci1yYWRpdXM9MTA%3D)


### 15. Clique no botão "Execute"

Clique no botão _"Execute"_ para realizar o envio da requisição ao servidor para efetivar o cadastro de uma nova tabela.

![Step 15 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/632f4b97-c186-4fbb-af44-03f6a63e55c7/8bbc62c2-beba-414a-ada2-7b356a49c516.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=36&mark-y=554&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMTE1Jmg9MzUmZml0PWNyb3AmY29ybmVyLXJhZGl1cz0xMA%3D%3D)


### 16. Após o envio da requisição, o resultado da execução será exibido na área de texto denominada "Response body"

O resultado da requisição informa as propriedades _"Code"_, neste caso _"201 - Created"_ e o _"Response body"_ em notação _"JSON"_ contendo o identificador único do registro criado. Reserve esta informação.

```json
{
  "uid": 45
}
```

![Step 16 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/a56aac08-65c9-4f6a-b263-ca95f06d4355/8731dc3f-bb69-4c71-9743-08720440ea20.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=104&mark-y=595&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMDQ3Jmg9NjQmZml0PWNyb3AmY29ybmVyLXJhZGl1cz0xMA%3D%3D)


ℹ️ Repita os passos 14, 15 e 16 para cada nova tabela a ser cadastrada.


## # Cadastrar Pacote de Extração e Carregamento de Dados


### 17. Clique em "Bundles"

Clique na opção _"Bundles"_ para acessar as funcionalidades de gerenciamento de cadastro de tabelas de pacotes de extração e carregamento de dados.

No pacote são definidas as tabelas a terem seus dados migrados, bem como usa ordem relacional.

As consultas _"sql"_ customizadas de extração e carregamento definidas a cada tabela do pacote são facultativas, caso não sejam informadas, o serviço vai gerar as consultas automaticamente usando a chave primária como filtro e considerando que todos os atributos serão extraídos e carregados.

![Step 17 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/7c4663b9-a71e-4d1a-8acf-e5514f079eee/7a9fd8dd-a839-455c-aa78-74dfc3875b66.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=19&mark-y=462&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMTQ5Jmg9NTkmZml0PWNyb3AmY29ybmVyLXJhZGl1cz0xMA%3D%3D)


### 18. Clique na funcionalidade "POST /v1/data/bundles"

A funcionalidade _"POST /v1/data/bundles"_ refere-se ao caso de uso: **RF03 – Gerenciar Cadastro de Pacotes de Extração e Carregamento de Dados – Inserir**.

![Step 18 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/0698c67c-fcf9-4c3e-960e-8cf6426deed5/ce8dad92-3864-4299-ac52-a60df3bc75c1.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=24&mark-y=656&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMDg3Jmg9MjkmZml0PWNyb3AmY29ybmVyLXJhZGl1cz0xMA%3D%3D)


### 19. Clique no botão "Try it out"

O botão _"Try it out"_ habilita a área de texto denominada _"Request Body"_ para informar o corpo da requisição em formato _"JSON"_, ou seja, informar os atributos de entrada necessários para realizar o cadastro de um novo pacote de extração e carregamento de dados.

![Step 19 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/b48fcfa8-4d90-4f2b-b2d8-d42f97c12c03/a6876328-576b-4528-8d18-b23379f0446d.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=1056&mark-y=290&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz05NCZoPTMxJmZpdD1jcm9wJmNvcm5lci1yYWRpdXM9MTA%3D)


### 20. Preencha a área de texto com o corpo da requisição em formato "JSON"

Preencha a área de texto denominada _"Request body"_ com o corpo da requisição em formato _"JSON"_ respeitando a especificação dos atributos definidos pelo serviço, disponível no recurso _"Schema"_ disponível na interface.

Para montar o pacote, será necessário informar:

*   _"bundleName"_: nome do pacote;

*   _"sourceDatasourceId"_: identificador único do banco de dados de origem, neste exemplo obtido no passo 8;

*   _"targetDatasourceId"_: identificador único do banco de dados de origem, neste exemplo obtido no passo 13;

*   _"bundledTables"_: lista das tabelas que vão compor o pacote de extração e carregamento;

    Para cada item da lista de tabelas do pacote, informar:

    *   _"sourceTableId"_: identificador único da tabela, neste exemplo obtido no passo 19;

    *   _"targetTableId"_: identificador único da tabela, neste exemplo obtido no passo 19;

    *   _"relationalOrdering"_: indicador numeral da ordem relacional de execução de extração e carregamento;


Exemplo de cadastro de pacote contendo apenas uma tabela:

```json
{
  "bundleName": "CO_APP_DB_MIGRATION",
  "sourceDatasourceId": 21,
  "targetDatasourceId": 22,
  "bundledTables": [
    {
      "sourceTableId": 45,
      "targetTableId": 45,
      "relationalOrdering": 1
    }
  ]
}
```

![Step 20 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/c3e08e1b-9ebb-45d3-8c77-03540050e1f7/c70bff4c-e0bf-43b7-b338-770bd0777c34.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=36&mark-y=419&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMTE1Jmg9MjMyJmZpdD1jcm9wJmNvcm5lci1yYWRpdXM9MTA%3D)


### 21. Clique no botão "Execute"

Clique no botão _"Execute"_ para realizar o envio da requisição ao servidor para efetivar o cadastro de um novo pacote de extração e carregamento de dados.

![Step 21 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/0115fd16-b4e8-47dd-b36d-9522e109cbe3/c322684e-65c7-4b33-a0a1-a10f96569c57.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=36&mark-y=683&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMTE1Jmg9MzUmZml0PWNyb3AmY29ybmVyLXJhZGl1cz0xMA%3D%3D)


### 22. Após o envio da requisição, o resultado da execução será exibido na área de texto denominada "Response body"

O resultado da requisição informa as propriedades _"Code"_, neste caso _"201 - Created"_ e o _"Response body"_ em notação _"JSON"_ contendo o identificador único do registro criado. Reserve esta informação.

```json
{
  "uid": 41
}
```

![Step 22 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/a88be867-fed6-446d-bb72-bc32b1fb10ff/da13a156-aaa5-4783-8dbb-4f52049d7b51.png?crop=focalpoint&fit=crop&fp-x=0.2503&fp-y=0.6106&fp-z=2.0000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=207&mark-y=606&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTQlMkNGRjc0NDImdz0yMDk0Jmg9MTI5JmZpdD1jcm9wJmNvcm5lci1yYWRpdXM9MTA%3D)


## # Executar e Consultar Tarefa de Extração e Carregamento de um Pacote


### 23. Clique em "Batch"

Clique na opção _"Batch"_ para acessar as funcionalidades de execução e consulta de processamentos em lote de um pacote de extração e carregamento de dados.

A partir da definição do pacote, será montada uma tarefa de processamento em lote. Para cada tabela definida no pacote, será montado uma etapa nesta tarefa seguindo a ordenação relacional contendo as consultas de extração e de carregamento configurado no cadastro do pacote.

Após a tarefa e suas respectivas etapas montadas, o serviço realiza a execução da extração e carregamento dos dados.

![Step 23 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/9d708c57-c91c-49cb-9039-8b0fde2ddc86/44a239af-e768-42f0-935e-d0c4dbd98386.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=19&mark-y=358&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMTQ5Jmg9NTkmZml0PWNyb3AmY29ybmVyLXJhZGl1cz0xMA%3D%3D)


### 24. Clique na funcionalidade "POST /v1/batch/job"

A funcionalidade _"POST /v1/data/bundles"_ refere-se ao caso de uso: **RF05 – Executar processamento em lote de um Pacote de Extração e Carregamento de dados**.

![Step 24 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/8ce8acb5-937b-43d3-aa9d-06881bfd38f6/abc08639-5fb8-44f2-8aa1-2f47bc3a78fa.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=24&mark-y=419&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMDg3Jmg9MjkmZml0PWNyb3AmY29ybmVyLXJhZGl1cz0xMA%3D%3D)


### 25. Clique no botão "Try it out"

O botão _"Try it out"_ habilita a área de texto denominada _"Request Body"_ para informar o corpo da requisição em formato _"JSON"_, ou seja, informar os atributos de entrada necessários para executar o processamento em lote de extração e carregamento de dados.

![Step 25 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/36e22c0a-183e-4de2-ae38-efd0a1d9f2dd/4a0fcdaa-452f-43bc-943a-7927b7e8cd62.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=1056&mark-y=454&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz05NCZoPTMxJmZpdD1jcm9wJmNvcm5lci1yYWRpdXM9MTA%3D)


### 26. Preencha a área de texto com o corpo da requisição em formato "JSON"

Preencha a área de texto denominada _"Request body"_ com o corpo da requisição em formato _"JSON"_ respeitando a especificação dos atributos definidos pelo serviço, disponível no recurso _"Schema"_ disponível na interface.

Para inicializar a execução de extração e carregamento de um pacote, será necessário informar:

*   _"dataBundleId"_: identificador único do pacote, neste exemplo obtido no passo 22;

![Step 26 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/ccd45e75-c27b-42d5-ac26-08612a4e5955/5cdc0b37-4117-47e9-b091-26a31652dd88.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=36&mark-y=343&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMTE1Jmg9MjMyJmZpdD1jcm9wJmNvcm5lci1yYWRpdXM9MTA%3D)


### 27. Clique no botão "Execute"

Clique no botão _"Execute"_ para realizar o envio da requisição ao servidor para inicializar o processamento em lote de um pacote de extração e carregamento de dados.

![Step 27 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/d98fd6ed-2911-48d6-a5da-517025350b76/e5350bd8-e28d-4b4e-b668-a362636127aa.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=36&mark-y=606&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMTE1Jmg9MzUmZml0PWNyb3AmY29ybmVyLXJhZGl1cz0xMA%3D%3D)


### 28. Após o envio da requisição, o resultado da execução será exibido na área de texto denominada "Response body"

O resultado da requisição informa as propriedades _"Code"_, neste caso _"201 - Created"_ e o _"Response body"_ em notação _"JSON"_ contendo o identificador único do registro criado. Reserve esta informação.

```json
{
  "uid": 29
}
```

![Step 28 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/a737089d-e87f-45df-a7f1-63718d1b00da/5adfd25a-9ca1-4b56-b36e-bde5104dac3f.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=104&mark-y=647&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMDQ3Jmg9NjQmZml0PWNyb3AmY29ybmVyLXJhZGl1cz0xMA%3D%3D)


### 29. Ainda em "Batch", clique na funcionalidade "GET /v1/batch/job/{jobExecutionId}"

A funcionalidade _"GET /v1/batch/job/{jobExecutionId}"_ refere-se ao caso de uso: **RF06 – Acompanhar execução do processamento em lote de um Pacote de Extração e Carregamento de dados**.

![Step 29 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/a964c292-855d-4718-8081-0aab43ad2587/bc411c2b-69c3-4155-874e-d5cb9546589a.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=24&mark-y=290&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMDg3Jmg9MjkmZml0PWNyb3AmY29ybmVyLXJhZGl1cz0xMA%3D%3D)


### 30. Clique no botão "Try it out"

O botão "_Try it out"_ habilita a caixa de texto denominada _"jobExecutionId"_ para informar o identificador único da tarefa de extração e carregamento de dados a ser consultada, parâmetro obrigatório para realizar esta requisição ao servidor.

![Step 30 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/01abb494-3865-4aea-8b36-71fb6eb11d17/20fa994c-2259-4f53-93f7-e8c9465ef317.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=1056&mark-y=326&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz05NCZoPTMxJmZpdD1jcm9wJmNvcm5lci1yYWRpdXM9MTA%3D)


### 31. Informe o parâmetro obrigatório da requisição "jobExecutionId"

Informe o parâmetro obrigatório da requisição _"jobExecutionId"_ com o identificador único da tarefa de extração e carregamento de dados recuperada no passo 28.

![Step 31 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/114daff3-e529-4321-8e84-826c7ec8d6db/86b0a7ae-b3ed-477e-a3b1-450b107d1ad9.png?crop=focalpoint&fit=crop&fp-x=0.2721&fp-y=0.6105&fp-z=1.8753&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=338&mark-y=356&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTQlMkNGRjc0NDImdz01MjUmaD02NiZmaXQ9Y3JvcCZjb3JuZXItcmFkaXVzPTEw)


### 32. Clique no botão "Execute"

Clique no botão _"Execute"_ para realizar o envio da requisição ao servidor para consultar a situação da execução da tarefa de extração e carregamento de dados.

![Step 32 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/a4024d85-f591-440f-b977-b32462c08126/86f41721-01e3-480f-981a-23316f96cbd7.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=36&mark-y=523&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMTE1Jmg9MzUmZml0PWNyb3AmY29ybmVyLXJhZGl1cz0xMA%3D%3D)


### 33. Após o envio da requisição, o resultado da execução será exibido na área de texto denominada "Response body"

O resultado da requisição informa as propriedades _"Code"_, neste caso _"200 - Ok"_ e o _"Response body"_ em notação _"JSON"_ contendo os detalhes da execução da tarefa.

```json
{
  "batchStatus": "COMPLETED",
  "jobExecutionId": 29,
  "jobName": "JOB-CO_APP_DB_MIGRATION",
  "jobStartTime": "22/10/2023 16:49",
  "jobEndTime": "22/10/2023 16:49",
  "jobElapsedTime": "00:00:00:268",
  "exitStatus": "COMPLETED",
  "exitMessage": "",
  "steps": [
    {
      "batchStatus": "COMPLETED",
      "stepName": "STEP-CUSTOMERS",
      "stepStartTime": "22/10/2023 16:49",
      "stepEndTime": "22/10/2023 16:49",
      "stepElapsedTime": "00:00:00:220",
      "commitCount": 5,
      "readCount": 392,
      "writeCount": 392,
      "writeSkipCount": 0,
      "exitStatus": "COMPLETED",
      "exitMessage": ""
    }
  ]
}
```

![Step 33 screenshot](https://images.tango.us/workflows/7f54464b-5b28-41ec-bfc2-dd3992112ddc/steps/4c7a42ce-75a9-48c4-8a31-41147029ed04/9fe7cd73-523f-4fb4-a656-fe5d5080376c.png?crop=focalpoint&fit=crop&fp-x=0.5000&fp-y=0.5000&w=1200&border=2%2CF4F2F7&border-radius=8%2C8%2C8%2C8&border-radius-inner=8%2C8%2C8%2C8&blend-align=bottom&blend-mode=normal&blend-x=0&blend-w=1200&blend64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL21hZGUtd2l0aC10YW5nby13YXRlcm1hcmstdjIucG5n&mark-x=104&mark-y=358&m64=aHR0cHM6Ly9pbWFnZXMudGFuZ28udXMvc3RhdGljL2JsYW5rLnBuZz9tYXNrPWNvcm5lcnMmYm9yZGVyPTMlMkNGRjc0NDImdz0xMDQ3Jmg9Mjk4JmZpdD1jcm9wJmNvcm5lci1yYWRpdXM9MTA%3D)


ℹ️ Para executar novamente o processamento em lote de extração e carregamento de dados, basta repetir os passos 26, 27 e 28.


✅ Após realizar estes passos, as tabelas definas no pacote de extração e carregamento de dados estarão atualizadas no banco de dados de destino. Fim do fluxo.

<br/>

***
Created with [Tango.us](https://tango.us?utm_source=markdown&utm_medium=markdown&utm_campaign=workflow%20export%20links)