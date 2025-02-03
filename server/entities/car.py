import json

class Car:
    def __init__(self, id, descricao, placa, modelo, ano, valorDiaria, ocupado):
        self.id = id
        self.descricao = descricao
        self.placa = placa
        self.modelo = modelo
        self.ano = ano
        self.valorDiaria = valorDiaria
        self.ocupado = ocupado

    def get_model_and_license_plate(self):
        return f"Modelo {self.modelo} - Placa {self.placa}"

    def set_id(self, new_id) -> None:
        self.id = new_id

    def get_year(self) -> int:
        return self.ano

    def get_daily_rate(self) -> int:
        return self.valorDiaria

    def is_rented(self) -> bool:
        return self.ocupado
    
    def set_rented(self, status) -> bool:
        self.ocupado = status

    def __str__(self) -> str:
        return f"{self.id} - {self.descricao}"
