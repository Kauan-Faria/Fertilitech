// src/pages/AdminDashboard.jsx
import styled from "styled-components";
import { useEffect, useState } from "react";
import api from "../api/api";
import Button from "../components/Button";

const Container = styled.div`
  background-color: #121212;
  color: white;
  min-height: 100vh;
  padding: 2rem;
`;

const Title = styled.h2`
  color: #e91e63;
  text-align: center;
  margin-bottom: 2rem;
`;

const CardList = styled.ul`
  list-style: none;
  padding: 0;
  margin: 0 auto;
  max-width: 800px;
`;

const Card = styled.li`
  background-color: #1e1e1e;
  padding: 1.5rem;
  border-radius: 10px;
  margin-bottom: 1.5rem;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
`;

const Actions = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  margin-top: 1rem;

  @media (min-width: 600px) {
    flex-direction: row;
  }
`;

const Input = styled.input`
  background-color: #fff;
  border-radius: 6px;
  border: 1px solid #ccc;
  padding: 0.5rem;
  width: 100%;
`;

const AdminDashboard = () => {
  const [appointments, setAppointments] = useState([]);
  const [error, setError] = useState(null);
  const [medications, setMedications] = useState({});

  useEffect(() => {
    fetchAppointments();
  }, []);

  const fetchAppointments = async () => {
    try {
      const response = await api.get("/appointments/all");
      setAppointments(response.data);
    } catch (err) {
      console.error(err);
      setError("Erro ao buscar consultas");
    }
  };

  const cancelAppointment = async (id) => {
    try {
      // Aqui você faria a exclusão no backend
      setAppointments(appointments.filter((a) => a.id !== id));
    } catch (err) {
      console.error(err);
      setError("Erro ao cancelar consulta");
    }
  };

  const prescribeMedication = async (id) => {
    const med = medications[id];
    if (!med || med.trim() === "") return alert("Digite o medicamento!");

    try {
      // Aqui você poderia enviar para o backend
      alert(`Medicamento "${med}" prescrito com sucesso!`);
      setMedications((prev) => ({ ...prev, [id]: "" }));
    } catch (err) {
      console.error(err);
      alert("Erro ao prescrever medicamento");
    }
  };

  return (
    <Container>
      <Title>Consultas Agendadas</Title>
      {error && <p style={{ color: "#f87171" }}>{error}</p>}

      <CardList>
        {appointments.map((a) => (
          <Card key={a.id}>
            <p><strong>Paciente:</strong> {a.paciente_nome}</p>
            <p><strong>Email:</strong> {a.paciente_email}</p>
            <p><strong>Telefone:</strong> {a.paciente_telefone}</p>
            <p><strong>Médico:</strong> {a.medico}</p>
            <p><strong>Motivo:</strong> {a.razao}</p>
            <p><strong>Data:</strong> {new Date(a.data_consulta).toLocaleString("pt-BR")}</p>

            <Actions>
              <Input
                placeholder="Prescrever medicamento"
                value={medications[a.id] || ""}
                onChange={(e) =>
                  setMedications((prev) => ({
                    ...prev,
                    [a.id]: e.target.value,
                  }))
                }
              />
              <Button onClick={() => prescribeMedication(a.id)}>Prescrever</Button>
              <Button
                onClick={() => cancelAppointment(a.id)}
                style={{ backgroundColor: "#dc2626" }}
              >
                Cancelar Consulta
              </Button>
            </Actions>
          </Card>
        ))}
      </CardList>
    </Container>
  );
};

export default AdminDashboard;
