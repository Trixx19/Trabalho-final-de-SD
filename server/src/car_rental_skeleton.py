import json
from .car_rental import CarRental
from entities.car import Car


class CarRentalSkeleton:

    car_rental = CarRental()

    @staticmethod
    def addCarro(args: str):
        new_carro = json.loads(args, object_hook= lambda dct : Car(**dct))
        CarRentalSkeleton.car_rental.addCarro(new_carro)
        
        return "sucess"
    
    @staticmethod
    def searchCarro(args: str):
        args_list = json.loads(args)
        carro_searched = CarRentalSkeleton.car_rental.searchCarro(args_list[0])

        if carro_searched is not None:
            carro_json = json.dumps(carro_searched.__dict__)
            return carro_json
        else:
            return "CarroNotExists"

    @staticmethod
    def getAllCarros(args: str):
        carro_list = CarRentalSkeleton.car_rental.getAllCarros()

        if len(carro_list) > 0:
            response_json = json.dumps([carro.__dict__ for carro in carro_list])
            return response_json
        else:
            return "CarroNotExists"

    @staticmethod
    def removeCarro(args: str):
        removed_carro = CarRentalSkeleton.car_rental.removeCarro(args)

        if removed_carro is not None:
            carro_json = json.dumps(removed_carro.__dict__)
            return carro_json
        else:
            return "CarroNotExists"
        
    @staticmethod
    def reserveCarro(args: str):
        reserve_status = CarRentalSkeleton.car_rental.reserveCarro(args)

        if reserve_status is not None:
            if reserve_status:
                return "sucess"
            else:
                return "InvalidOccupancyStatus"
        else:
            return "CarroNotExists"
        
    @staticmethod
    def cancelReservation(args: str):
        reserve_status = CarRentalSkeleton.car_rental.cancelReservation(args)

        if reserve_status is not None:
            if reserve_status:
                return "sucess"
            else:
                return "InvalidOccupancyStatus"
        else:
            return "CarroNotExists"
        
    

        
