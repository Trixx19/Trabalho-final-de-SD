from entities.message import Message
from .car_rental_skeleton import CarRentalSkeleton



class Despacher:

    remote_objects = {
        "AluguelCarros": CarRentalSkeleton,
    }

    @staticmethod
    def _get_class(class_name):
        if class_name in Despacher.remote_objects:
            return Despacher.remote_objects[class_name]
        
        else:
            print(" > Requisição Inválida!")
            print("Classe " + class_name  + " inválida!")

    @staticmethod
    def _get_method(class_object, method_name):
        if hasattr(class_object, method_name):
            method_reference = getattr(class_object, method_name)
            return method_reference
        else:
            print(" > Requisição Inválida!")
            print("Método " + method_name  + " inválido!")

    @staticmethod
    def handle_request(message_client: Message):
        class_object = Despacher.remote_objects[message_client.objectReference]
        method = Despacher._get_method(class_object, message_client.methodId)

        response_args = method(message_client.arguments)
        return response_args

