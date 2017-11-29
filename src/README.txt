Author - nxu3128@rit.edu     Neha Upadhyay

Problem statement -

Imagine we want to measure the salinity, water temperature, air temperature along
with accompanying pictures on a grid in the ocean.  We decide to do this with buoys,
each of which has an internal network of sensors attached to a radio link for outside
communication.    We need a reliable transfer protocol to ensure our packets arrive on
shore safely.  Your goal is to design and demonstrate a protocol that can guarantee the
delivery of the packets.  The packets produced must also move easily through the regular
Internet.  The resulting protocol must perform better than TCP with smaller packets and
hopefully fewer packets sent.


This program implements a reliable transfer protocol to ensure our packets arrive on
shore safely. The goal is to design and demonstrate a protocol that can guarantee the
delivery of the packets. The packets produced must also move easily through the regular
Internet. The resulting protocol must perform better than TCP with smaller packets and
hopefully fewer packets sent.


Design -
There are two classes ClientProtocol and ServerProtocol.
The ServerProtocol class has to run first and then the ClientProtocol class will run. 

The path of the image in the ClientProtocol class to be given as command line argument
each time when it is executed. It makes use of input stream and bytearrayoutputstream
to convert the points in the image in bytes and then it makes use of datagram packet
and socket to transfer the image from client to server.


The path of the image where it will be stored has to be given as a command line
argument each time it is executed. The packet from the client will be received
and output stream will be used to write the packets simultaneously  when
they are received by the server.


The client protocol also takes in the last two bytes of the buffer send as acknowledgement
number and sequence number. It also asks for to measure salinity, air temperature and water
temperature which acts as handshaking phenomena to establish connection.

The server protocol then takes the buffer puts the values of the image in another buffer
and takes out the last two bytes and prints out the acknowledgement number and sequence
number. The server and client window can be seen and compared in order to check for the
sequence and acknowledgement number correctness.