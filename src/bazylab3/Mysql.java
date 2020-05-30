package bazylab3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Mysql
	{
		Connection baza;
		Permissions user;
		String url;

		public Mysql(Permissions user, String ip, int port)
			{
				this.user = user;
				this.url = "jdbc:mysql://"+ ip + ":" + port + "/lab3?serverTimezone=UTC";
				connect();
			}

		public void connect()
			{
				try
				{
					baza = DriverManager.getConnection(url, user.username, user.password);

				} catch (SQLException ex)
				{
					debug(ex);
				}
				System.out.print("polaczono z baza\n");
			}

		
		public String[] getColumns(String query)
			{
					Statement zapytanie;
					try
					{
						zapytanie = baza.createStatement();
						ResultSet wynik = zapytanie.executeQuery(query);
						ResultSetMetaData wynikMeta = wynik.getMetaData();
						int kolumny = wynikMeta.getColumnCount();
						String output[] = new String[kolumny];
						for (int i = 1; i <= kolumny; i++)
						{
							output[i - 1] = wynikMeta.getColumnName(i);
						}
						return output;
					} catch (SQLException ex)
					{
						debug(ex);
					}

					return null; // just in case

				}
		public String[] getColumnTypes(String query)
			{
					Statement zapytanie;
					try
					{
						zapytanie = baza.createStatement();
						ResultSet wynik = zapytanie.executeQuery(query);
						ResultSetMetaData wynikMeta = wynik.getMetaData();
						int kolumny = wynikMeta.getColumnCount();
						String output[] = new String[kolumny];
						for (int i = 1; i <= kolumny; i++)
						{
							output[i - 1] = wynikMeta.getColumnClassName(i);
						}
						return output;
					} catch (SQLException ex)
					{
						debug(ex);
					}

					return null; // just in case

				}

		public String[] getTables()
			{
				String temp[][] = select("show tables;");
				String kolumna[] = new String[temp.length];
				for(int i = 0;i<kolumna.length;i++)
				{
					kolumna[i] = temp[i][0];
				}
				return kolumna;
			}

		public String[][] select(String query)
			{
				try
				{
					Statement zapytanie = baza.createStatement();
					ResultSet wynik = zapytanie.executeQuery(query);
					ResultSetMetaData wynikMeta = wynik.getMetaData();
					int kolumny = wynikMeta.getColumnCount();
					int n = 0;

					// tutaj jest hack zeby dostac liczbe rekordow zwroconych, w sumie to nie wiem
					// czy da sie lepiej zrobic
					while (wynik.next())
					{
						n++;
					}
					while (wynik.previous())
					{
					}

					String output[][] = new String[n][kolumny];
					n = 0;
					while (wynik.next())
					{
						for (int i = 1; i <= kolumny; i++)
						{
							// debug output
//							if (i > 1)
//								System.out.print(",  ");
							String pole = wynik.getString(i);
//							System.out.print(pole);

							output[n][i-1] = pole;
						}
						n++;
//						System.out.print("\n"); //debug output
					}
					return output;
				} catch (SQLException ex)
				{
					debug(ex);
				}
				return null; // just incase
			}

		public boolean insert(String input[], String into[], String Tabela)
			{
				return true;
			}

		public void close()
			{
				try
				{
					baza.close();
				} catch (SQLException ex)
				{
					debug(ex);
				}
			}

		
		
		
		public void debug(SQLException ex) //bo mnie wkurzaja te try catche
			{
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
	}
