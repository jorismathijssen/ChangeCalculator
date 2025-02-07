import { fetchChange } from "../services/apiService";
import { describe, it, expect, vi } from "vitest";

describe("apiService", () => {

  it("1. Returns the correct change when API call is successful", async () => {
    global.fetch = vi.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve({ changeAmount: 5, changeBreakdown: { "€5": 1 } }),
      })
    );

    const result = await fetchChange(10, 15, "EUR");

    expect(result.changeAmount).toBe(5);
    expect(result.changeBreakdown["€5"]).toBe(1);

    global.fetch.mockRestore();
  });

  it("2. Throws an error when the API request fails", async () => {
    global.fetch = vi.fn(() => Promise.reject(new Error("API Failure")));

    await expect(fetchChange(10, 15, "EUR")).rejects.toThrow(
      "An error occurred while fetching change data. Please try again later."
    );

    global.fetch.mockRestore();
  });

});
