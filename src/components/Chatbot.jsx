import React, { useState, useRef, useEffect } from "react";
import styled from "styled-components";
import { FiMessageCircle, FiSend } from "react-icons/fi";

const ChatToggle = styled.button`
  position: fixed;
  bottom: 20px;
  right: 20px;
  background-color: #b03060;
  color: white;
  border: none;
  border-radius: 50%;
  padding: 14px;
  cursor: pointer;
  font-size: 1.5rem;
  box-shadow: 0 0 12px rgba(0, 0, 0, 0.4);

  &:hover {
    background-color: #8a244c;
  }
`;

const ChatWindow = styled.div`
  position: fixed;
  bottom: 80px;
  right: 20px;
  width: 320px;
  height: 450px;
  background: #2a2a2a;
  border-radius: 16px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.5);
  display: flex;
  flex-direction: column;
`;

const ChatHeader = styled.div`
  background: #b03060;
  padding: 1rem;
  border-radius: 16px 16px 0 0;
  font-weight: bold;
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const CloseButton = styled.button`
  background: transparent;
  border: none;
  color: white;
  font-size: 1.2rem;
  cursor: pointer;
  padding: 0;
  line-height: 1;
`;

const ChatMessages = styled.div`
  flex: 1;
  padding: 1rem;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
`;

const Message = styled.div`
  max-width: 80%;
  padding: 0.6rem 1rem;
  border-radius: 12px;
  font-size: 0.9rem;
  align-self: ${(props) => (props.sender === "user" ? "flex-end" : "flex-start")};
  background: ${(props) => (props.sender === "user" ? "#b03060" : "#444")};
  color: white;
  word-wrap: break-word;
`;

const ChatInputWrapper = styled.div`
  display: flex;
  padding: 0.8rem;
  border-top: 1px solid #444;
`;

const ChatInput = styled.input`
  flex: 1;
  padding: 0.6rem;
  border-radius: 8px;
  border: none;
  outline: none;
  font-size: 1rem;

  &::placeholder {
    color: #bbb;
  }
`;

const SendButton = styled.button`
  margin-left: 0.6rem;
  background: #b03060;
  color: white;
  border: none;
  border-radius: 8px;
  padding: 0.6rem;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;

  &:hover:enabled {
    background: #8a244c;
  }

  &:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }
`;

export default function Chatbot() {
  const [isChatOpen, setIsChatOpen] = useState(false);
  const [messages, setMessages] = useState([
    { id: 1, sender: "bot", text: "Olá! 👋 Como posso te ajudar hoje?" },
  ]);
  const [input, setInput] = useState("");
  const [isWaitingResponse, setIsWaitingResponse] = useState(false);
  const messagesEndRef = useRef(null);

  // Scroll para a última mensagem quando mensagens mudam
  useEffect(() => {
    if (messagesEndRef.current) {
      messagesEndRef.current.scrollIntoView({ behavior: "smooth" });
    }
  }, [messages]);

  const handleSendMessage = () => {
    if (!input.trim() || isWaitingResponse) return;

    const userMessage = { id: Date.now(), sender: "user", text: input.trim() };
    setMessages((prev) => [...prev, userMessage]);
    setInput("");
    setIsWaitingResponse(true);

    setTimeout(() => {
      const botMessage = {
        id: Date.now() + 1,
        sender: "bot",
        text: "Recebi sua mensagem, já já te respondo!",
      };
      setMessages((prev) => [...prev, botMessage]);
      setIsWaitingResponse(false);
    }, 1000);
  };

  return (
    <>
      <ChatToggle aria-label="Abrir chat" onClick={() => setIsChatOpen((prev) => !prev)}>
        <FiMessageCircle />
      </ChatToggle>

      {isChatOpen && (
        <ChatWindow role="dialog" aria-modal="true" aria-labelledby="chatbot-header">
          <ChatHeader id="chatbot-header">
            Chatbot FertiliTech
            <CloseButton aria-label="Fechar chat" onClick={() => setIsChatOpen(false)}>
              ✕
            </CloseButton>
          </ChatHeader>
          <ChatMessages>
            {messages.map((msg) => (
              <Message key={msg.id} sender={msg.sender}>
                {msg.text}
              </Message>
            ))}
            <div ref={messagesEndRef} />
          </ChatMessages>
          <ChatInputWrapper>
            <ChatInput
              type="text"
              value={input}
              placeholder="Digite sua mensagem..."
              onChange={(e) => setInput(e.target.value)}
              onKeyDown={(e) => e.key === "Enter" && handleSendMessage()}
              aria-label="Campo de mensagem"
              disabled={isWaitingResponse}
            />
            <SendButton onClick={handleSendMessage} disabled={isWaitingResponse} aria-label="Enviar mensagem">
              <FiSend />
            </SendButton>
          </ChatInputWrapper>
        </ChatWindow>
      )}
    </>
  );
}