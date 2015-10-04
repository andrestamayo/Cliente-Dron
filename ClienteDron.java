import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.StringTokenizer;

import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import de.yadrone.base.command.CommandManager;
import de.yadrone.base.command.LEDAnimation;
import de.yadrone.base.exception.ARDroneException;
import de.yadrone.base.exception.IExceptionListener;
import de.yadrone.base.navdata.AttitudeListener;

public class ClienteDron
{

	public static void main(String[] args)
	{
		IARDrone drone = null;
		try
		{
			// Tutorial Section 1
			drone = new ARDrone();
			drone.addExceptionListener(new IExceptionListener() {
				public void exeptionOccurred(ARDroneException exc)
				{
					exc.printStackTrace();
				}
			});
			drone.start();
			CommandManager a = drone.getCommandManager();
			//socket por el que recibimos una cadena de caracteres que nos indica el comando (x) el tiempo  (y) y la velocidad (z)
			Socket miCliente;
			InputStream salida;
			DataInputStream flujo = null;
			 try {
			 miCliente = new Socket( "maquina",2000 );
			 salida = miCliente.getInputStream();
			  flujo = new DataInputStream( salida );
			 
			 } catch( IOException e ) {
			 System.out.println( e );
			 }
			 		
			

			while(true){
				String comando = flujo.readUTF();
				execute(a, comando);
			}
			

		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		finally
		{
			if (drone != null)
				drone.stop();

			System.exit(0);
		}
	}

	private static void execute(CommandManager a, String comando) {
		// TODO Auto-generated method stub
		StringTokenizer st = new StringTokenizer(comando);
		String orden = st.nextToken();
		int tiemmpo = Integer.parseInt(st.nextToken());
		int speed=Integer.parseInt(st.nextToken());
		if(comando.equals("AVANZA")){
			
			a.forward(speed).doFor(tiemmpo);
			
		}else if(orden.equals("RETROCEDE")){
			
			a.backward(speed).doFor(tiemmpo);
			
		}else if(orden.equals("DERECHA")){
			
			a.goRight(speed).doFor(tiemmpo);
			
		}else if(orden.equals("IZQUIERDA")){
			
			a.goLeft(speed).doFor(tiemmpo);
			
		}else if(orden.equals("ARRIBA")){
			
			a.up(speed).doFor(tiemmpo);
			
		}else if(orden.equals("DESPEGA")){
			
			a.takeOff();
			
		}else if(orden.equals("ATERRIZA")){
			
			a.landing();
			
		}else if(orden.equals("ROTAI")){
			
			a.spinLeft(speed).doFor(tiemmpo);
			
		}else if(orden.equals("ROTAD")){
			
			a.spinRight(speed).doFor(tiemmpo);
			
		} 
	}
}
