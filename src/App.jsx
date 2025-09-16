// src/App.jsx
import { BrowserRouter as Router, Routes, Route, useLocation } from "react-router-dom";
import PatientSignup from "./pages/PatientSignup";
import AppointmentBooking from "./pages/AppointmentBooking";
import PatientDashboard from "./pages/PatientDashboard";
import AdminLogin from "./pages/AdminLogin";
import AdminDashboard from "./pages/AdminDashboard";
import AdminAppointments from "./pages/AdminAppointments";
import LandingPage from "./pages/LandingPage";
import Navbar from "./components/Navbar";
import PatientLogin from "./pages/PatientLogin";

function AppRoutes() {
  const location = useLocation();

  // Oculta navbar na LandingPage e rotas de admin
  const hideNavbar =
    location.pathname === "/" ||
    location.pathname === "/patient/login" ||
    location.pathname === "/signup" ||
    location.pathname === "/dashboard" ||
     location.pathname === "/appointment" ||
    location.pathname.startsWith("/admin");


  return (
    <>
      {!hideNavbar && <Navbar />}
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/patient/login" element={<PatientLogin />} />
        <Route path="/signup" element={<PatientSignup />} />
        <Route path="/appointment" element={<AppointmentBooking />} />
        <Route path="/dashboard" element={<PatientDashboard />} />
        <Route path="/admin/login" element={<AdminLogin />} />
        <Route path="/admin/dashboard" element={<AdminDashboard />} />
        <Route path="/admin/appointments" element={<AdminAppointments />} />
      </Routes>
    </>
  );
}

function App() {
  return (
    <Router>
      <AppRoutes />
    </Router>
  );
}

export default App;
