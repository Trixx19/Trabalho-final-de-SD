class Message:
    def __init__(self, messageType, id, objectReference, methodId, arguments):
        self.messageType = messageType
        self.id = id
        self.objectReference = objectReference
        self.methodId = methodId
        self.arguments = arguments