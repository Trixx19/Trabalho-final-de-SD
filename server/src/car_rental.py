from entities.car import Car

class CarRental:

    def __init__(self):
        self.current_id = 0
        self.carros = []
        
    def addCarro(self, carro: Car) -> bool:
        carro.set_id(self.current_id)
        self.current_id += 1

        self.carros.append(carro)
        for carro in self.carros:
            print(carro.get_model_and_license_plate())


    def searchCarro(self, placa):
        for carro in self.carros:
            if carro.placa == placa:
                return carro
        return None



    def getAllCarros(self):
        return self.carros
    
    def removeCarro(self, IDCarro):
        for index, carro in enumerate(self.carros):
            if carro.id == int(IDCarro):
                return self.carros.pop(index)

        return None
    
    def reserveCarro(self, IDCarro):
        for carro in self.carros:
            if carro.id == int(IDCarro):
                if carro.is_rented():
                    return False
                else:
                    carro.set_rented(True)
                    return True

        return None
    
    def cancelReservation(self, IDCarro):
        for carro in self.carros:
            if carro.id == int(IDCarro):
                if not carro.is_rented():
                    return False
                else:
                    carro.set_rented(False)
                    return True

        return None