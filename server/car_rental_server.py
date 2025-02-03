import json
import socket as sk
from entities.message import Message
from src.despacher import Despacher
from src.message_historic import MessageHistoric

# Responsável pelo tratamento de mensagens duplicadas
historicMap = MessageHistoric()

def packs_message(id, objectReference, methodId, arguments):
    response_message = dict()

    response_message["messageType"] = 1
    response_message["id"] = id
    response_message["objectReference"] = objectReference
    response_message["methodId"] = methodId
    response_message["arguments"] = arguments

    response_json = json.dumps(response_message)
    return response_json


def unpack_message(client_request):
    client_request = client_request.decode('utf-8')
    client_message = json.loads(client_request, object_hook=lambda dct : Message(**dct))

    return client_message

def handle_client(client_adress, client_request, socket_udp):
    client_ip, client_port = client_adress
    client_message = unpack_message(client_request)

    # Faço a verificação das mensagens duplicadas
    saved_historic = historicMap.verify_duplication(client_ip, client_port, client_message.id)

    # Verifico se a mensagem é duplicada, caso seja, apenas reenvio, caso contrário processo a requisição
    if saved_historic is not None:
        socket_udp.sendto(saved_historic.encode(), client_adress)
    else:
        response_args = Despacher.handle_request(client_message)

        response_json = packs_message(client_message.id, 
                                    client_message.objectReference, 
                                    client_message.methodId, 
                                    response_args)
        
        # Salvo a mensagem no histórico de mensagens
        historicMap.update_historic(client_ip, client_port, id, response_json)

        socket_udp.sendto(response_json.encode(), client_adress)

def main():
    server_adress = ("localhost", 3456)
    socket_udp = sk.socket(sk.AF_INET, sk.SOCK_DGRAM)
    socket_udp.bind(server_adress)

    print(f"Server online in {server_adress[0]}:{server_adress[1]}")

    while True:
        client_request, client_adress = socket_udp.recvfrom(1024)
        handle_client(client_adress, client_request, socket_udp)


if __name__ == "__main__":
    main()
