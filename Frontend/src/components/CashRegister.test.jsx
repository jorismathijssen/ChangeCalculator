import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import { describe, it, expect, vi } from "vitest";
import CashRegister from "./CashRegister";

describe("CashRegister Component", () => {
  
  it("1. Renders the application correctly", () => {
    render(<CashRegister />);
    expect(screen.getByText(/Wisselgeld Systeem/i)).toBeInTheDocument();
  });

  it("2. Displays an error message when input is invalid", async () => {
    render(<CashRegister />);

    fireEvent.click(screen.getByRole("button", { name: /Bereken/i }));

    const errorMessages = await screen.findAllByText(/Ongeldige invoer/i);
    expect(errorMessages[0]).toBeInTheDocument();
  });

  it("3. Calls the fetchChange API when valid input is provided", async () => {
    global.fetch = vi.fn(() =>
      Promise.resolve({
        ok: true,
        json: () =>
          Promise.resolve({ changeAmount: 5, changeBreakdown: { "€5": 1 } }),
      })
    );

    render(<CashRegister />);

    fireEvent.change(screen.getByPlaceholderText(/Voer aankoopbedrag in/i), {
      target: { value: "10" },
    });
    fireEvent.change(screen.getByPlaceholderText(/Voer betaald bedrag in/i), {
      target: { value: "15" },
    });

    fireEvent.click(screen.getByRole("button", { name: /Bereken/i }));

    expect(await screen.findByText(/Wisselgeld:/i)).toBeInTheDocument();
    expect(await screen.findByText(/€5/i)).toBeInTheDocument();

    global.fetch.mockRestore();
  });

  it("4. Resets input fields when the Reset button is clicked", () => {
    render(<CashRegister />);

    fireEvent.change(screen.getByPlaceholderText(/Voer aankoopbedrag in/i), {
      target: { value: "10" },
    });
    fireEvent.change(screen.getByPlaceholderText(/Voer betaald bedrag in/i), {
      target: { value: "15" },
    });

    fireEvent.click(screen.getByRole("button", { name: /Reset/i }));

    expect(screen.getByPlaceholderText(/Voer aankoopbedrag in/i)).toHaveValue(null);
    expect(screen.getByPlaceholderText(/Voer betaald bedrag in/i)).toHaveValue(null);
  });

  it("5. Displays the correct change breakdown after API response", async () => {
    global.fetch = vi.fn(() =>
      Promise.resolve({
        ok: true,
        json: () =>
          Promise.resolve({ changeAmount: 10, changeBreakdown: { "€5": 2 } }),
      })
    );

    render(<CashRegister />);

    fireEvent.change(screen.getByPlaceholderText(/Voer aankoopbedrag in/i), {
      target: { value: "10" },
    });
    fireEvent.change(screen.getByPlaceholderText(/Voer betaald bedrag in/i), {
      target: { value: "20" },
    });

    fireEvent.click(screen.getByRole("button", { name: /Bereken/i }));

    expect(await screen.findByText(/Totaal: 10.00/)).toBeInTheDocument();
    expect(await screen.findByText(/€5/)).toBeInTheDocument();
    expect(await screen.findByText(/2x/)).toBeInTheDocument();

    global.fetch.mockRestore();
  });

  it("6. Updates currency symbol when a different currency is selected", () => {
    render(<CashRegister />);

    fireEvent.change(screen.getByRole("combobox"), { target: { value: "USD" } });
    expect(screen.getByText(/Aankoopbedrag \(\$\)/i)).toBeInTheDocument();

    fireEvent.change(screen.getByRole("combobox"), { target: { value: "GBP" } });
    expect(screen.getByText(/Aankoopbedrag \(£\)/i)).toBeInTheDocument();
  });

  it("7. Does not allow negative values in input fields", () => {
    render(<CashRegister />);
  
    const purchaseInput = screen.getByPlaceholderText(/Voer aankoopbedrag in/i);
    const cashInput = screen.getByPlaceholderText(/Voer betaald bedrag in/i);
  
    fireEvent.change(purchaseInput, { target: { value: "-10" } });
    fireEvent.change(cashInput, { target: { value: "-5" } });
  
    expect(purchaseInput.value).not.toContain("-");
    expect(cashInput.value).not.toContain("-");
  });  

});
