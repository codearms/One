


VMware Fusion 11.1.0 (for Intel-based Macs)
https://www.vmware.com/go/downloadfusion-cn


VMware Workstation Pro 15.1.0 for Windows
VMware Workstation Pro 15.1.0 for Linux

https://www.vmware.com/go/downloadworkstation-cn


15221394017@163.com
M15221394017@163.com




https://www.microsoft.com/zh-cn/software-download
https://www.macos-download.com/
https://ubuntu.com


Mavericks
Yosemite
El Capitan
macOS Sierra
High Sierra



虚拟机的网络连接设置有三种，桥接，NAT和仅主机。

桥接模式：

1、“桥接模式”的网络类型：guest1的虚拟网卡和宿主机的物理网卡进行通信，物理网卡再与外部物理交换机进行通信，然后物理交换机再与宿主机 物理网卡 通信，最 后再与guest2的虚拟网卡通信。

2、该类型支持虚拟机之间、虚拟机和宿主机之间、虚拟机和外部host通信；

3、虚拟机自己必须拥有外部网络的IP地址（即外部物理交换机中网段的IP）。

优缺点：

1、配置简单；

2、虚拟机、宿主机以及物理交换机必须在同一个网段。如果宿主机连接不同的网络，就要去更改虚拟机的网络配置，比较麻烦。

NAT模式：

1、“NAT”类型， 称为网络地址转换，在“仅主机”类型的基础上提供了可以访问外部host的能力；

2、虚拟机之间、虚拟机和宿主机之间、虚拟机和外部host之间都可以进行通信；


3、虚拟机的IP只需要配置NAT网段中的IP，访问外部host可以通过宿主机IP访问。它不需要有外部网络独立的IP（即物理交换机网段中的IP）。

优缺点：

1、  虚拟机的网络配置确定后，就可以很少变动。宿主机连接的网络变化，不影响到虚拟机。因为NAT不变。


仅主机模式：

1、“仅主机”类型，guest1、2通过虚拟网卡和虚拟交换机进行通信。同时，该虚拟交换机还和虚拟出来的宿主机网卡进行通信；

2、该类型，只适用于虚拟机之间以及虚拟机和主机之间的通信，和其他外部host机器隔离；


3、该类型，可以不用插网线。

优缺点：

1、  和外部网络做隔离


2、  无法和外部主机进行通信；