import { createGlobalStyle } from "styled-components";

const GlobalStyles = createGlobalStyle`
  *,*::before,*::after{box-sizing:border-box;margin:0;padding:0;}
  html{font-size:16px;scroll-behavior:smooth;}
  body{font-family:Inter,system-ui,Arial,sans-serif;background:${({theme})=>theme.colors.background};color:${({theme})=>theme.colors.text};}
  a{color:inherit;text-decoration:none;}
  img{max-width:100%;display:block;}
`;
export default GlobalStyles;
