from datetime import datetime

from fastapi import FastAPI

app = FastAPI(
    title="AgroVani AI Service",
    description="ML inference service for AgroVani: intent classification and plant disease detection",
    version="0.1.0",
)


@app.get("/health")
def health():
    return {
        "status": "UP",
        "service": "agrovani-ai",
        "timestamp": datetime.now().isoformat(),
    }