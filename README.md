# EA project
# Overroll architect and technical uses
## Spring boot
## Feign Client
## Spring cloud

# Flow from client/browser to server
## All request from client/browser will go through APIGateway. APIGateway will route the traffic to individual service
## The call from service to service will use feign client to connect. Feign client also has configuration to set timeout 
if there is any a call which take more than 5 seconds then it will throw a timeout exception.
