import Container from "../components/Layout";
import styled from "styled-components";
import { useEffect, useState } from "react";
import axios from "axios";

const List = styled.ul`
  list-style: none;
  padding: 0;
  margin-top: 2rem;
`;

const Card = styled.li`
  background: white;
  padding: 1rem;
  margin-bottom: 1rem;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
`;

const AdminAppointments = () => {
  const [appointments, setAppointments] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios.get("http://localhost:4000/appointments/all")
      .then(res => setAppointments(res.data))
      .catch(err => {
        console.error(err);
        setError("Erro ao carregar consultas");
      });
  }, []);

  return (
    <Container>
      <h2>Consultas Agendadas</h2>
      {error && <p style={{ color: "#DC2626" }}>{error}</p>}
      <List>
        {appointments.map(a => (
          <Card key={a.id}>
            <p><strong>Paciente:</strong> {a.fullname}</p>
            <p><strong>Médico:</strong> {a.doctor}</p>
            <p><strong>Data:</strong> {new Date(a.date).toLocaleString()}</p>
          </Card>
        ))}
      </List>
    </Container>
  );
};
export default AdminAppointments;