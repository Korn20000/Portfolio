using UnityEngine;
using MySql.Data;
using MySql.Data.MySqlClient;
using System;
using System.Data;
using System.Collections;
using System.Collections.Generic;

public class DatabaseAdapter : MonoBehaviour
{
	//public DatabaseAdapter(){}

    // MySQL instance specific items
	string constr = "Server=db4free.net;Database=userdatabasesep4;UID=anonymoushinigam;Password=Kohta1;Pooling=false;Port=3306;Character Set=utf8";

    // connection object
    MySqlConnection con = null;
    // command object
    MySqlCommand cmd = null;
    // reader object
    MySqlDataReader rdr = null;
    // object collection array
	public void ConnectToDB()
    {
        try
        {
            // setup the connection element
            con = new MySqlConnection(constr);

            // lets see if we can open the connection
            con.Open();
            Debug.Log("Connection State: " + con.State);
        }
        catch (Exception ex)
        {
            Debug.Log(ex.ToString());
        }

    }

    public void DisconnectFromDB()
    {
        Debug.Log("killing con");
        if (con != null)
        {
            if (con.State.ToString() != "Closed")
                con.Close();
            con.Dispose();
        }
    }

    // Insert new entries into the table
	public void InsertEntries(string mail)
    {
        string query = string.Empty;
        // Error trapping in the simplest form
        try
        {
            query = "INSERT INTO persons (mail) VALUES (?mail)";
            //if (con.State.ToString() != "Open")
                //con.Open();
            using (con)
            {
                    using (cmd = new MySqlCommand(query, con))
                    {
                        MySqlParameter oParam1 = cmd.Parameters.Add("?mail", MySqlDbType.VarChar);
						oParam1.Value = mail;
                        cmd.ExecuteNonQuery();
                    }
                }
            }
        catch (Exception ex)
        {
            Debug.Log(ex.ToString());
        }
        finally
        {
        }
    }

    // Read all entries from the table
	public string ReadMailByID(Int32 ID)
    {
        string query = string.Empty;
		string _returnMail = string.Empty;
        try
        {
			query = "SELECT mail FROM persons WHERE ID = ?id";
            if (con.State.ToString() != "Open")
                con.Open();
            using (con)
            {
                using (cmd = new MySqlCommand(query, con))
                {
					MySqlParameter oParam1 = cmd.Parameters.Add("?id", MySqlDbType.Int32);
					oParam1.Value = ID;
                    rdr = cmd.ExecuteReader();
                    if (rdr.HasRows)
                        while (rdr.Read())
                        {
							_returnMail = rdr["mail"].ToString();
                        }
                    rdr.Dispose();
                }
            }

        }
        catch (Exception ex)
        {
			//Debug.Log ("ReadMailByID Exception");
            Debug.Log(ex.ToString());
        }
        finally
        {
        }
		return _returnMail;
    }
}