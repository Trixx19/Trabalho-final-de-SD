class MessageHistoric:

    def __init__(self):
        self.message_historic = {}

    def update_historic(self, ip_adress, port, id, message):
        # Atualizo a ultima mensagem de um determinado IP/PORTA
        # Salvo o ID da mensagem no valor, para verificar posteriormente se a mensagem sofreu duplicação
        key = (ip_adress, port)
        value = (message, id)
        self.message_historic[key] = value

    def verify_duplication(self, ip_adress, port, id):
        # Verifico se há uma mensagem do IP/Porta com o ID buscado
        # Caso exista, é uma mensagem duplicada, retorno o conteúdo sem fazer o processamento da requisição
        searched_key = (ip_adress, port)

        if searched_key in self.message_historic:
            message_id = self.message_historic[searched_key][1]

            if message_id == id:
                return self.message_historic[searched_key][0]
            
        return None