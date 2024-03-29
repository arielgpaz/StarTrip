  <h1 align="center">★StarTrip★</h1>
  <h3 align="center">★Que a diversão esteja com você!★</h3>

# Documentação

## Estrutura de Classes do Domínio

A estrutura de classes abaixo, representa o que é esperado que seja persistido pela aplicação. Nem sempre as interfaces REST vão representar exatamente a estrutura de classes do domínio.

- Endereco
    - id;
    - cep;
    - logradouro;
    - numero;
    - complemento;
    - bairro;
    - cidade;
    - estado;

- Usuario
    - id;
    - nome;
    - email;
    - senha;
    - cpf;
    - dataNascimento;
    - Endereco endereco;

- CaracteristicaImovel
    - id;
    - descricao;

- Imovel
    - id;
    - identificacao;
    - TipoImovel tipoImovel
        - Apartamento
        - Casa
        - Hotel
        - Pousada
    - Endereco endereco;
    - Usuario proprietario;
    - List \<CaracteristicaImovel\> caracteristicas;

- FormaPagamento
    - Cartão de Crédito
    - Cartão de Débito
    - Pix
    - Dinheiro

- Anuncio
    - id;
    - TipoAnuncio tipoAnuncio;
        - Completo
        - Quarto
    - Imovel imovel;
    - Usuario anunciante;
    - valorDiaria;
    - List\<FormaPagamento\> formasAceitas;
    - descricao;

- Periodo
    - dataHoraInicial;
    - dataHoraFinal;

- Reserva
    - id;
    - Usuario solicitante;
    - Anuncio anuncio;
    - Periodo periodo;
    - quantidadePessoas;
    - dataHoraReserva;
    - Pagamento pagamento;

- Pagamento
    - valorTotal;
    - FormaPagamento formaEscolhida;
    - StatusPagamento statusPagamento;
        - Pendente
        - Pago
        - Estornado
        - Cancelado

# Funcionalidades

## 1. Usuário

### 1.1. Cadastro de Usuário
- Assinatura
    - `POST /usuarios`
- Parâmetros de Entrada:
    - Usuario
        - nome*
        - email*
        - senha*
        - cpf*
        - dataNascimento*
        - Endereco endereco
            - cep*
            - logradouro*
            - numero*
            - complemento
            - bairro*
            - cidade*
            - estado*
- Saída esperada em caso de sucesso:
    - Status: `201 CREATED`
    - Body:
        - Objeto `Usuario` contendo todos os campos cadastrados exceto o campo senha
- Regras
    - Os atributos marcados com um "*" são obrigatórios. A validação deve ser realizada usando Bean Validations.
    - O campo CPF deve ser representado como uma String
    - Não deve ser possível cadastrar mais de um usuário com o mesmo E-Mail
        - Caso já exista outro usuário com o e-mail informado, deve lançar uma exceção que retorne o status 400 e uma mensagem informando o problema
        - Mensagem: `Já existe um recurso do tipo Usuario com E-Mail com o valor '{substituir-por-email-informado}'.`
            - Ex: `Já existe um recurso do tipo Usuario com E-Mail com o valor 'teste1@teste.com'.`
    - Não deve ser possível cadastrar mais de um usuário com o mesmo CPF
        - Caso já exista outro usuário com o CPF informado, deve lançar uma exceção que retorne o status 400 e uma mensagem informando o problema
        - Mensagem: `Já existe um recurso do tipo Usuario com CPF com o valor '{substituir-por-cpf-informado}'.`
            - Ex: `Já existe um recurso do tipo Usuario com CPF com o valor '12345678900'.`
    - O campo CEP deve aceitar somente o formato 99999-999. A validação deve ser realizada usando Bean Validations.
        - Caso seja informado um CEP com outro formato, deve ser retornado um erro com a mensagem: `O CEP deve ser informado no formato 99999-999.`
    - O campo CPF deve aceitar somente o formato 99999999999. A validação deve ser realizada usando Bean Validations.
        - Caso seja informado um CPF com outro formato, deve ser retornado um erro com a mensagem: `O CPF deve ser informado no formato 99999999999.`
    - Os campos Cidade e Estado serão representados como String
    - Caso seja informado um endereço, então os campos marcados com * são obrigatórios. A validação deve ser realizada usando Bean Validations.

### 1.2. Listar usuários
- Assinatura
    - `GET /usuarios`
- Parâmetros de Entrada
    - N/A
- Saída esperada em caso de sucesso
    - Status: `200 SUCCESS`
    - Body
        - List\<Usuario>
            - Usuario
                - id
                - nome
                - email
                - cpf
                - dataNascimento
                - Endereco endereco
                    - id
                    - cep
                    - logradouro
                    - numero
                    - complemento
                    - bairro
                    - cidade
                    - estado
- Regras
    - O campo senha não deve ser retornado, porém o objeto a ser retornado é o `Usuario`
- Desafio
    - Listar os usuários com paginação, em ordem alfabética pelo nome por padrão

### 1.3. Buscar um usuário por id
- Assinatura
    - `GET /usuarios/{idUsuario}`
- Parâmetros de Entrada:
    - idUsuario (path parameter)
- Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body:
        - Usuario
            - id
            - nome
            - email
            - cpf
            - dataNascimento
            - Endereco endereco
                - id
                - cep
                - logradouro
                - numero
                - complemento
                - bairro
                - cidade
                - estado
- Comportamentos:
    - A aplicação deve obter o usuário através do id informado.
        - Caso nenhum usuário seja localizado, deve lançar uma exceção que retorne o status 404
        - Mensagem: `Nenhum(a) Usuario com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
            - Ex: `Nenhum(a) Usuario com Id com o valor '999' foi encontrado.`
- Regras
    - O campo senha não deve ser retornado, porém o objeto a ser retornado é o `Usuario`

### 1.4. Buscar um usuário por cpf
- Assinatura
    - `GET /usuarios/cpf/{cpf}`
- Parâmetros de Entrada:
    - cpf (path parameter)
- Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body:
        - Usuario
            - id
            - nome
            - email
            - cpf
            - dataNascimento
            - Endereco endereco
                - id
                - cep
                - logradouro
                - numero
                - complemento
                - bairro
                - cidade
                - estado
- Comportamentos:
    - A aplicação deve obter o usuário através do CPF informado.
        - Caso nenhum usuário seja localizado, deve lançar uma exceção que retorne o status 404
        - Mensagem: `Nenhum(a) Usuario com CPF com o valor '{SUBSTITUIR-PELO-CPF-INFORMADO}' foi encontrado.`
            - Ex: `Nenhum(a) Usuario com CPF com o valor '01245487848' foi encontrado.`
- Regras
    - O campo senha não deve ser retornado, porém o objeto a ser retornado é o `Usuario`

### 1.5. Alterar um usuário
- Assinatura
    - `PUT /usuarios/{id}`
- Parâmetros de Entrada:
    - id (path parameter)
    - AtualizarUsuarioRequest
        - nome*
        - email*
        - senha*
        - dataNascimento*
        - Endereco endereco
            - cep*
            - logradouro*
            - numero*
            - complemento
            - bairro*
            - cidade*
            - estado*
- Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body:
        - Objeto `Usuario` contendo todos os campos exceto o campo senha
- Regras
    - Os atributos marcados com um "*" são obrigatórios. A validação deve ser realizada usando Bean Validations.
    - Não deve ser possível alterar o CPF de um usuário já cadastrado
    - A aplicação deve obter o usuario através do Id informado.
        - Caso nenhum usuario seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema
        - Mensagem: `Nenhum(a) Usuario com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
            - Ex: `Nenhum(a) Usuario com Id com o valor '999' foi encontrado.`
    - Não deve ser possível cadastrar mais de um usuário com o mesmo E-Mail
        - Caso já exista outro usuário com o e-mail informado, deve lançar uma exceção que retorne o status 400 e uma mensagem informando o problema
        - Mensagem: `Já existe um recurso do tipo Usuario com E-Mail com o valor '{substituir-por-email-informado}'.`
            - Ex: `Já existe um recurso do tipo Usuario com E-Mail com o valor 'teste1@teste.com'.`
    - O campo CEP deve aceitar somente o formato 99999-999. A validação deve ser realizada usando Bean Validations.
        - Caso seja informado um CEP com outro formato, deve ser retornado um erro com a mensagem: `O CEP deve ser informado no formato 99999-999.`
    - Os campos Cidade e Estado serão representados como String
    - O campo senha não deve ser retornado

### 1.6. Obter imagem de avatar para usuários

Será necessário adaptar a entidade de usuário para que ela passe a ter um novo atributo que represente a imagem de avatar dentro do sistema. Este atributo vai guardar a URL de uma imagem.

Este atributo não será recebido pelo objeto de request. A aplicação deverá realizar a chamada à uma API externa que retorna essa URL e então vincular ao usuário que estiver sendo criado.

API que será usada:
- GET https://some-random-api.ml/img/dog
- Retorno:
  ```json
  {
      "link": "https://i.some-random-api.ml/kC1VFB2J2F.jpg"
  }
  ```

Sugestão de biblioteca para implementar essa integração: Feign Client

Referências:
- https://cloud.spring.io/spring-cloud-openfeign/reference/html/
- https://www.baeldung.com/spring-cloud-openfeign

## 2. Imóvel

### 2.1. Cadastro de Imóvel
- Assinatura
    - `POST /imoveis`
- Parâmetros de Entrada:
    - CadastrarImovelRequest
        - TipoImovel tipoImovel*
        - Endereco endereco*
            - cep*
            - logradouro*
            - numero*
            - complemento
            - bairro*
            - cidade*
            - estado*
        - identificacao*
        - idProprietario*
        - List \<CaracteristicaImovel\> caracteristicas
            - CaracteristicaImovel
                - descricao
- Saída esperada em caso de sucesso:
    - Status: `201 CREATED`
    - Body: Objeto Imovel contendo todos os campos
- Comportamentos:
    - A aplicação deve obter o usuario através do Id informado, para poder vincular ao Imovel antes de persistir.
        - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema
        - Mensagem: `Nenhum(a) Usuario com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
            - Ex: `Nenhum(a) Usuario com Id com o valor '999' foi encontrado.`
- Regras
    - Os atributos marcados com um "*" são obrigatórios. A validação deve ser realizada usando Bean Validations.
    - O campo "identificacao" serve pra que o proprietário identifique o imóvel textualmente. Algo como "Casa da praia".
    - O campo CEP deve aceitar somente o formato 99999-999. A validação deve ser realizada usando Bean Validations.
        - Caso seja informado um CEP com outro formato, deve ser retornado um erro com a mensagem: `O CEP deve ser informado no formato 99999-999.`
    - Os campos Cidade e Estado serão representados como String

### 2.2. Listar imóveis
- Assinatura
    - `GET /imoveis`
- Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body:
        - Lista de Imóveis
            - É esperado que sejam exibidos todos os atributos do Imóvel, bem como os atributos de Endereço, proprietário e características.
- Desafio
    - Listar os imóveis com paginação, em ordem alfabética pelo campo identificacao por padrão

### 2.3. Listar imóveis de um proprietário específico
- Assinatura
    - `GET /imoveis/proprietarios/{idProprietario}`
- Parâmetros de Entrada:
    - idProprietario (path parameter)
- Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body:
        - Lista de Imóveis
            - É esperado que sejam exibidos todos os atributos do Imóvel, bem como os atributos de Endereço, proprietário e características.
- Comportamentos:
    - O sistema deve retornar somente os imóveis do proprietário informado
- Desafio
    - Listar os imóveis com paginação, em ordem alfabética pelo campo identificacao por padrão

### 2.4. Buscar um imóvel por id
- Assinatura
    - `GET /imoveis/{idImovel}`
- Parâmetros de Entrada:
    - idImovel (path parameter)
- Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: Objeto Imóvel
        - É esperado que sejam exibidos todos os atributos do Imóvel, bem como os atributos de Endereço, proprietário e características.
- Comportamentos:
    - A aplicação deve obter o Imóvel através do Id informado.
        - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema
        - Mensagem: `Nenhum(a) Imovel com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
            - Ex: `Nenhum(a) Imovel com Id com o valor '999' foi encontrado.`

### 2.5. Excluir um imóvel
- Assinatura
    - `DELETE /imoveis/{idImovel}`
- Parâmetros de Entrada:
    - idImovel (path parameter)
- Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: vazio
- Comportamentos:
    - A aplicação deve excluir o Imóvel através do Id informado.
        - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema
        - Mensagem: `Nenhum(a) Imovel com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
            - Ex: `Nenhum(a) Imovel com Id com o valor '999' foi encontrado.`
- Não deve ser possível excluir um imóvel que possua algum anúncio
    - Caso o imóvel possuia anúncios, o sistema deve lançar uma exceção com o status 400 e uma mensagem informando o problema
        - Mensagem: `Não é possível excluir um imóvel que possua um anúncio.`

### 2.6. Alterar a operação de excluir imóvel para que seja feita a exclusão lógica

Este desafio consiste em modificar o funcionamento da exclusão de imóveis para que funcione através do mecanismo de exclusão lógica, o mesmo adotado no item 3.4.

## 3. Anúncio

### 3.1 Anunciar imóvel
- Assinatura
    - `POST /anuncios`
- Parâmetros de Entrada:
    - CadastrarAnuncioRequest
        - idImovel*
        - idAnunciante*
        - TipoAnuncio tipoAnuncio*
        - valorDiaria*
        - formasAceitas*
        - descricao*
- Saída esperada em caso de sucesso:
    - Status: `201 CREATED`
    - Body: Anuncio
- Comportamentos:
    - A aplicação deve obter o Imóvel através do Id informado.
        - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema
        - Mensagem: `Nenhum(a) Imovel com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
            - Ex: `Nenhum(a) Imovel com Id com o valor '999' foi encontrado.`
    - A aplicação deve obter o anunciante (Usuario) através do Id informado.
        - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema
        - Mensagem: `Nenhum(a) Usuario com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
            - Ex: `Nenhum(a) Usuario com Id com o valor '999' foi encontrado.`
- Regras
    - Os atributos marcados com um "*" são obrigatórios. A validação deve ser realizada usando Bean Validations.
    - Não deve ser possível criar mais de um anúncio para o mesmo imóvel
        - Caso já exista algum anúncio para o mesmo imóvel, a aplicação deve lançar uma exceção que retorne o código 400 e uma mensagem.
            - Mensagem: `Já existe um recurso do tipo Anuncio com IdImovel com o valor '{substituir-pelo-idimovel-informado}'.`
                - Ex: `Já existe um recurso do tipo Anuncio com IdImovel com o valor '12'.`

### 3.2. Listar anúncios
- Assinatura
    - `GET /anuncios`
- Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: Lista de Anuncio
        - É esperado que sejam exibidos todos os atributos do Anuncio, bem como os atributos de Imóvel e anunciante.
- Comportamentos:
    - Deve listar todos os anúncios armazenados no banco de dados
- Desafio
    - Listar os anúncios com paginação, em ordem pelo valor da diária (valores menores primeiro)

### 3.3. Listar anúncios de um anunciante específico
- Assinatura
    - `GET /anuncios/anunciantes/{idAnunciante}`
- Parâmetros de Entrada:
    - idAnunciante (path parameter)
- Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: Lista de Anuncio
        - É esperado que sejam exibidos todos os atributos do Anuncio, bem como os atributos de Imóvel e anunciante.
- Comportamentos:
    - O sistema deve retornar somente os anúncios que tenham sido realizados pelo anunciante informado
- Desafio
    - Listar os anúncios com paginação, em ordem pelo valor da diária (valores menores primeiro)

### 3.4. Excluir um anúncio
- Assinatura
    - `DELETE /anuncios/{idAnuncio}`
- Parâmetros de Entrada:
    - idAnuncio (path parameter)
- Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: vazio
- Comportamentos:
    - A aplicação deve obter o Anúncio através do Id informado.
        - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema
        - Mensagem: `Nenhum(a) Anuncio com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
            - Ex: `Nenhum(a) Anuncio com Id com o valor '999' foi encontrado.`
    - O anúncio não deve ser removido do banco de dados. Ao invés disso, deve ser feita uma exclusão lógica do registro. Faça as adaptações necessárias para que o registro permaneça na base mas não seja mais considerado em nenhuma outra busca. Dica: Possivelmente será necessário refatorar alguns métodos criados anteriormente.

## 4. Reserva

### 4.1. Realizar uma reserva
- Assinatura
    - `POST /reservas`
- Parâmetros de Entrada:
    - CadastrarReservaRequest
        - idSolicitante*
        - idAnuncio*
        - Periodo periodo*
        - quantidadePessoas*
- Saída esperada em caso de sucesso:
    - Status: `201 CREATED`
    - Body:
        - InformacaoReservaResponse
            - idReserva
            - DadosSolicitanteResponse solicitante
                - id
                - nome
            - quantidadePessoas
            - DadosAnuncioResponse anuncio
                - id
                - Imovel
                - Usuario anunciante
                - List\<FormaPagamento\> formasAceitas
                - descricao
            - Periodo periodo (e todos os seus atributos)
            - Pagamento pagamento (e todos os seus atributos)
- Comportamentos:
    - O formato da data esperado no período é: `yyyy-MM-dd HH:mm:ss`
    - Deve ser definida e registrada a Data/Hora do momento em que a reserva foi realizada
    - A aplicação aceita somente reservas iniciando as 14:00 e finalizando as 12:00. Caso seja informado um horário diferente, a aplicação de sobrescrever a informação e considerar estes horários arbitrariamente.
    - Deve ser calculado o valor total para o pagamento, baseado no valor da diária e na quantidade de diárias solicitadas da reserva
    - A aplicação deve obter o usuario solicitante através do Id informado, para poder vincular à reserva antes de persistir.
        - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema
        - Mensagem: `Nenhum(a) Usuario com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
            - Ex: `Nenhum(a) Usuario com Id com o valor '999' foi encontrado.`
    - A aplicação deve obter o anuncio através do Id informado, para poder vincular à reserva antes de persistir.
        - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema
        - Mensagem: `Nenhum(a) Anuncio com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
            - Ex: `Nenhum(a) Anuncio com Id com o valor '999' foi encontrado.`
- Regras
    - Os atributos marcados com um "*" são obrigatórios. A validação deve ser realizada usando Bean Validations.
    - Não deve ser possível reservar um imóvel com um período cuja data final seja menor que a data inicial
        - Caso ocorra essa situação, a aplicação deve lançar uma exceção com o status 400 com uma mensagem informando o problema
            - Mensagem: `Período inválido! A data final da reserva precisa ser maior do que a data inicial.`
    - Não deve ser possível reservar um imóvel com um período cuja diferença entre a data final e inicial seja menor que 1 dia
        - Caso ocorra essa situação, a aplicação deve lançar uma exceção com o status 400 com uma mensagem informando o problema
            - Mensagem: `Período inválido! O número mínimo de diárias precisa ser maior ou igual à 1.`
    - Não deve ser possível reservar um imóvel cujo solicitante seja o mesmo anunciante
        - Caso ocorra essa situação, a aplicação deve lançar uma exceção com o status 400 com uma mensagem informando o problema
            - Mensagem: `O solicitante de uma reserva não pode ser o próprio anunciante.`
    - Não deve ser possível reservar um imóvel que já possua uma reserva ativa no mesmo período.
        - Observe que o período pode possuir sobreposição em apenas uma data. Mesmo assim não deve ser possível realizar a reserva.
        - O conceito de reserva "ativa" consiste em uma reserva que não tenha sido estornada nem cancelada.
        - Caso ocorra essa situação, a aplicação deve lançar uma exceção com o status 400 com uma mensagem informando o problema
            - Mensagem: `Este anuncio já esta reservado para o período informado.`
    - Caso a reserva seja de um Hotel, o número mínimo de pessoas é 2
        - Caso seja informado um número inferior, deve lançar uma exceção com o status 400 com uma mensagem informando o problema
            - Mensagem: `Não é possivel realizar uma reserva com menos de {NUMERO-MINIMO-PESSOAS} pessoas para imóveis do tipo {TIPO-INFORMADO}`
                - Ex: `Não é possivel realizar uma reserva com menos de 2 pessoas para imóveis do tipo Hotel`
    - Caso a reserva seja de uma Pousada, o número mínimo de diárias é 5
        - Caso seja informado um período inferior, deve lançar uma exceção com o status 400 com uma mensagem informando o problema
            - Mensagem: `Não é possivel realizar uma reserva com menos de {NUMERO-MINIMO-DIARIAS} diárias para imóveis do tipo {TIPO-INFORMADO}`
                - Ex: `Não é possivel realizar uma reserva com menos de 5 diárias para imóveis do tipo Pousada`

### 4.2. Listar reservas de um solicitante específico
- Assinatura
    - `GET /reservas/solicitantes/{idSolicitante}`
- Parâmetros de Entrada:
    - idSolicitante (path parameter)
    - Periodo periodo (opcional)
- Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: List de Reserva
        - É esperado que sejam exibidos todos os atributos da Reserva, bem como os atributos de Anuncio e Soliciante.
- Comportamentos:
    - O sistema deve retornar somente as reservas do solicitante informado
    - Caso nenhum período seja informado, o sistema deve retornar todas as reservas. Considere sempre o período completo, se for informada apenas uma das duas datas, o sistema vai considerar que nenhuma data foi informada.
    - Caso seja informado um período, o sistema deve retornar somente as reservas cujas datas esteja dentro do período informado.
- Desafio
    - Listar as reservas com paginação, ordenando pela data do fim da reserva(Datas maiores primeiro).

### 4.3. Listar reservas de um anunciante específico
- Assinatura
    - `GET /reservas/anuncios/anunciantes/{idAnunciante}`
- Parâmetros de Entrada:
    - idAnunciante (path parameter)
- Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: List de Reserva
        - É esperado que sejam exibidos todos os atributos da Reserva, bem como os atributos de Anuncio e Soliciante.
- Comportamentos:
    - O sistema deve retornar somente as reservas do anunciante informado
- Desafio
    - Listar as reservas com paginação, ordenando pela data do fim da reserva(Datas maiores primeiro).

### 4.4. Pagar reserva
- Assinatura
    - `PUT /reservas/{idReserva}/pagamentos`
- Parâmetros de Entrada:
    - idReserva (path parameter)
    - formaPagamento
- Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: vazio
- Comportamentos:
    - A aplicação deve obter a Reserva através do Id informado.
        - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema
        - Mensagem: Nenhum(a) Reserva com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.
            - Ex: Nenhum(a) Reserva com Id com o valor '999' foi encontrado.
    - Alterar o status do Pagamento da reserva para "Pago"
- Regras
    - Não deve ser possível realizar um pagamento com uma forma de pagamento que não seja aceita pelo anúncio.
        - Caso isso aconteça, deve lançar uma exceção com o status 400 e uma mensagem informando o problema
            - Mensagem: `O anúncio não aceita {FORMA-PAGAMENTO-INFORMADA} como forma de pagamento. As formas aceitas são {FORMAS-ACEITAS-PELO-ANUNCIO}.`
                - Ex: `O anúncio não aceita PIX como forma de pagamento. As formas aceitas são DINHEIRO, CARTAO_CREDITO.`
    - Não deve ser possível realizar o pagamento de uma reserva paga, estornada ou cancelada
        - Caso esteja em algum desses status, deve lançar uma exceção com o status 400 e uma mensagem informando o problema
            - Mensagem: `Não é possível realizar o pagamento para esta reserva, pois ela não está no status PENDENTE.`

### 4.5. Cancelar uma reserva
- Assinatura
    - `PUT /reservas/{idReserva}/pagamentos/cancelar`
- Parâmetros de Entrada:
    - idReserva (path parameter)
- Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: vazio
- Comportamentos:
    - A aplicação deve obter a Reserva através do Id informado.
        - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema
        - Mensagem: `Nenhum(a) Reserva com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
            - Ex: `Nenhum(a) Reserva com Id com o valor '999' foi encontrado.`
    - Alterar o status do Pagamento da reserva para "Cancelado"
- Regras
    - Não deve ser possível realizar o cancelamento de uma reserva paga, estornada, cancelada
        - Caso esteja em algum desses status, deve lançar uma exceção com o status 400 e uma mensagem informando o problema
            - Mensagem: `Não é possível realizar o cancelamento para esta reserva, pois ela não está no status PENDENTE.`

### 4.6. Estornar reserva
- Assinatura
    - `PUT /reservas/{idReserva}/pagamentos/estornar`
- Parâmetros de Entrada:
    - idReserva (path parameter)
- Saída esperada em caso de sucesso:
    - Status: `200 SUCCESS`
    - Body: vazio
- Comportamentos:
    - A aplicação deve obter a Reserva através do Id informado.
        - Caso nenhum seja localizado, deve lançar uma exceção com o status 404 e uma mensagem informando o problema
        - Mensagem: `Nenhum(a) Reserva com Id com o valor '{SUBSTITUIR-PELO-ID-INFORMADO}' foi encontrado.`
            - Ex: `Nenhum(a) Reserva com Id com o valor '999' foi encontrado.`
    - Alterar o status do Pagamento da reserva para "Estornado"
    - A forma de pagamento escolhida deve ser removida
- Regras
    - Não deve ser possível estornar o pagamento de uma reserva pendente, estornada, cancelada
        - Caso esteja em algum desses status, deve lançar uma exceção com o status 400 e uma mensagem informando o problema
            - Mensagem: `Não é possível realizar o estorno para esta reserva, pois ela não está no status PAGO.`
