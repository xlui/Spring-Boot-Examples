namespace java app.xlui.example.thrift.service

service HelloService {
    string sayHello(1:string username)
    string sayBye(1:string username)
}