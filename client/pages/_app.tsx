import '../styles/globals.css'
import type { AppProps } from 'next/app'
import 'bootstrap/dist/css/bootstrap.min.css'
import Footer from '../src/components/Footer';
import Top from '../src/components/Top';

function MyApp({ Component, pageProps }: AppProps) {
  return (
    <div>
      <Top/>
      <Component {...pageProps} />
      <Footer/>
    </div>
  );
}
export default MyApp
